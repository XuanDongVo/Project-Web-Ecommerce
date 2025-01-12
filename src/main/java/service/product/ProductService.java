package service.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import dbConnection.DBConnection;
import dto.request.AddProductInDatabaseRequest;
import dto.request.MultipleOptionsProductRequest;
import dto.response.PaginationResponse;
import dto.response.ProductDetailResponse;
import entity.Category;
import entity.Color;
import entity.Inventory;
import entity.Product;
import entity.ProductColorImage;
import entity.ProductSku;
import entity.SubCategory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.cart.CartDetailRepository;
import repository.category.CategoryRepository;
import repository.category.SubCategoryRepository;
import repository.color.ColorRepository;
import repository.inventory.InventoryRepository;
import repository.order.OrderDetailRepository;
import repository.product.ProductColorImgRepository;
import repository.product.ProductRepository;
import repository.product.ProductSkuRepository;
import repository.size.SizeRepository;
import service.cartdetail.CartDetailService;
import utils.MultipleOptionsSQLQueryBuilder;

public class ProductService {

	private ProductSkuRepository productSkuRepository = new ProductSkuRepository();
	private InventoryRepository inventoryRepository = new InventoryRepository();
	private ProductRepository productRepository = new ProductRepository();
	private SubCategoryRepository subCategoryRepository = new SubCategoryRepository();
	private CategoryRepository categoryRepository = new CategoryRepository();
	private ColorRepository colorRepository = new ColorRepository();
	private ProductColorImgRepository productColorImgRepository = new ProductColorImgRepository();
	private SizeRepository sizeRepository = new SizeRepository();
	private OrderDetailRepository orderDetailRepository = new OrderDetailRepository();
	private CartDetailRepository cartDetailRepository = new CartDetailRepository();
	private CartDetailService cartDetailService = new CartDetailService();

	// lay ra tat ca san pham
	public List<ProductDetailResponse> getAllProduct() {
		List<Product> products = new ArrayList<>();

		products = productRepository.getAllProduct();

		// Lấy danh sách ID của sản phẩm từ trang kết quả
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});
		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;
	}

	// xoa san pham
	public void deleteProduct(Long productId, HttpServletRequest request, HttpServletResponse response) {
		Connection connection = null;

		try {
			connection = DBConnection.getConection();
			connection.setAutoCommit(false); // Bắt đầu giao dịch
			List<ProductSku> productSkus = productSkuRepository.findByProductId(productId);

			for (ProductSku productSku : productSkus) {
				// xoa san pham trong inventory
				inventoryRepository.removeByProductSkuId(connection, productSku.getId());
				// xoa trong orderDetail
				orderDetailRepository.removeByProductSkuId(connection, productSku.getId());
				// xoa trong cartDetail
				cartDetailRepository.removeByProductSkuId(connection, productSku.getId());
				// xoa trong cookies neu ton tai

				// xoa sku
				productSkuRepository.removeByProductSkuId(connection, productSku.getId());
			}
			// xoa productcolorimg
			productColorImgRepository.removeByProductId(connection, productId);
			if (connection.isClosed()) {
				System.out.println("close");
			}
			// xoa product
			productRepository.removeById(connection, productId);
			if (connection.isClosed()) {
				System.out.println("close");
			}

			connection.setAutoCommit(true);

		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback(); // Rollback toàn bộ giao dịch
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			throw new RuntimeException("Giao dịch thất bại: " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close(); // Đóng kết nối
				} catch (SQLException closeEx) {
					closeEx.printStackTrace();
				}
			}
		}

	}

	public void updateProductColorImg(Long productColorImgId, String img, String color) throws SQLException {
		Connection connection = null;
		try {
			connection = DBConnection.getConection();
			connection.setAutoCommit(false); // Bắt đầu giao dịch

			Color currentColor = colorRepository.findByName(color);
			if (currentColor == null) {
				throw new RuntimeException("Màu không tồn tại: " + color);
			}

			int rowEffect = productColorImgRepository.updateColor(connection, currentColor.getId(), productColorImgId);
			if (rowEffect == 0) {
				throw new RuntimeException("Không thể cập nhật product.");
			}

			ProductColorImage productColorImage = productColorImgRepository.findById(productColorImgId);
			if (productColorImage == null) {
				throw new RuntimeException("Không tìm thấy productColorImage với ID: " + productColorImgId);
			}

			boolean isUpdate = updateImgInServer(img, productColorImage.getImage());
			if (!isUpdate) {
				throw new RuntimeException("Cập nhật hình ảnh thất bại cho productColorImgId: " + productColorImgId);
			}

			connection.setAutoCommit(true);
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback(); // Rollback giao dịch nếu lỗi
			}
			throw new RuntimeException("Giao dịch thất bại: " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				DBConnection.closeConnection(connection); // Đóng kết nối
			}
		}
	}

	// update product
	public void updateProduct(ProductDetailResponse productDetailResponse) throws SQLException {
		Connection connection = null;
		try {
			connection = DBConnection.getConection();
			connection.setAutoCommit(false); // Bắt đầu giao dịch

			SubCategory subCategory = subCategoryRepository.findByName(productDetailResponse.getSubCategory())
					.orElseThrow(() -> new RuntimeException("Not found"));

			// update Product
			int rowEffect;
			rowEffect = productRepository.updateProduct(connection, productDetailResponse, subCategory);

			if (rowEffect == 0) {
				throw new RuntimeException("Không thể cập nhật product.");
			}

			List<ProductColorImage> productColorImages = productColorImgRepository
					.finbByProductId(productDetailResponse.getId());

			for (ProductColorImage pci : productColorImages) {
				rowEffect = productSkuRepository.updatePrice(connection, productDetailResponse.getPrice(), pci.getId());
				if (rowEffect == 0) {
					throw new RuntimeException("Không thể cập nhật productSku.");
				}
			}

			connection.setAutoCommit(true);

		} catch (Exception e) {
			connection.rollback(); // Rollback toàn bộ giao dịch
			throw new RuntimeException("Giao dịch thất bại: " + e.getMessage(), e);

		} finally {
			if (connection != null) {
				DBConnection.closeConnection(connection);
			}
		}
	}

	// Adđ Product
	public void addProduct(AddProductInDatabaseRequest productInDatabaseRequest) {
		Connection connection = null;
		try {
			connection = DBConnection.getConection();
			connection.setAutoCommit(false); // Bắt đầu giao dịch

			// lưu prouduct
			Product product = new Product();
			product.setName(productInDatabaseRequest.getName());
			product.setDescription(productInDatabaseRequest.getDescription());
			SubCategory subCategory = subCategoryRepository.findByName(productInDatabaseRequest.getSubCategory()).get();
			product.setSubCategory(subCategory);
			long productId = productRepository.addProduct(connection, product);
			if (productId == 0) {
				throw new RuntimeException("Không thể tạo product: Không có ID product được sinh ra.");
			}
			if (connection.isClosed()) {
				System.out.println("close");
			}
			// lưu productColorImg
			for (AddProductInDatabaseRequest.ProductSkuResponse productColorImg : productInDatabaseRequest.getskus()) {
				saveProductColorImgAndSku(connection, productId, productColorImg, productInDatabaseRequest);
			}

			connection.setAutoCommit(true); // Hoàn tất giao dịch
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback(); // Rollback toàn bộ giao dịch
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			throw new RuntimeException("Giao dịch thất bại: " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close(); // Đóng kết nối
				} catch (SQLException closeEx) {
					closeEx.printStackTrace();
				}
			}
		}
	}

	private void saveProductColorImgAndSku(Connection connection, long productId,
			AddProductInDatabaseRequest.ProductSkuResponse productColorImg, AddProductInDatabaseRequest request) {
		try {
			String imageUrl = saveBase64Image(productColorImg.getImage(), request.getName(),
					productColorImg.getColor());
			// Xử lý logic lưu ProductColorImage
			ProductColorImage productColorImage = new ProductColorImage();
			productColorImage.setProduct(new Product(productId, null, null, null));
			productColorImage.setImage(imageUrl);
			productColorImage.setColor(colorRepository.findByName(productColorImg.getColor()));

			long productColorImgId = productColorImgRepository.addProductColorImg(connection, productColorImage);
			if (productColorImgId == 0) {
				throw new RuntimeException("Không thể tạo productColorImg.");
			}

			// Xử lý logic lưu ProductSku và Inventory
			productColorImg.getSizeAndStock().forEach((size, stock) -> {
				try {
					ProductSku productSku = new ProductSku();
					productSku.setPrice(request.getPrice());
					productSku.setSize(sizeRepository.findByName(size));
					productSku.setProductColorImage(new ProductColorImage(productColorImgId, null, null, null));

					long productSkuId = productSkuRepository.addProductSku(connection, productSku);
					if (productSkuId == 0) {
						throw new RuntimeException("Không thể tạo ProductSku.");
					}

					Inventory inventory = new Inventory();
					ProductSku newProductSku = new ProductSku();
					newProductSku.setId(productSkuId);
					inventory.setProductSku(newProductSku);
					inventory.setStock(stock);
					inventoryRepository.addStockInInventory(connection, inventory);
				} catch (Exception e) {
					throw new RuntimeException("Lỗi khi xử lý ProductSku hoặc Inventory: " + e.getMessage(), e);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Lỗi khi xử lý ProductColorImg hoặc ProductSku: " + e.getMessage(), e);
		}
	}

	private boolean updateImgInServer(String base64Image, String currentImg) {
		try {
			// Loại bỏ prefix "data:image"
			if (base64Image.startsWith("data:image")) {
				base64Image = base64Image.split(",")[1];
			}

			// Đường dẫn thư mục lưu trữ ảnh
			String directoryPath = "E:\\Eclipse\\Do_An_Web\\src\\main\\webapp\\imgProduct";

			// Lấy fileName từ currentImg
			String fileName = currentImg.substring(currentImg.lastIndexOf("/") + 1);

			// Kiểm tra fileName có tồn tại trong thư mục
			File directory = new File(directoryPath);
			File[] files = directory.listFiles();
			if (files == null || files.length == 0) {
				System.out.println("Thư mục không chứa file nào.");
				return false;
			}

			boolean fileExists = false;
			for (File file : files) {
				if (file.getName().equals(fileName)) {
					fileExists = true;
					break;
				}
			}

			if (!fileExists) {
				System.out.println("File không tồn tại trong thư mục.");
				return false;
			}

			// Giải mã chuỗi Base64 thành mảng byte
			byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

			// Ghi lại ảnh vào file
			File targetFile = new File(directoryPath + File.separator + fileName);
			try (FileOutputStream fos = new FileOutputStream(targetFile)) {
				fos.write(decodedBytes);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String saveBase64Image(String base64Image, String productName, String color) throws IOException {

		if (base64Image.startsWith("data:image")) {
			base64Image = base64Image.split(",")[1]; // Loại bỏ prefix
		}

		// Giải mã chuỗi Base64 thành mảng byte
		byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

		// Tạo tên file dựa trên tên sản phẩm và màu sắc
		String fileName = productName.replaceAll(" ", "_") + "_" + color.replaceAll(" ", "_") + ".jpg";

		String directoryPath = "E:\\Eclipse\\Do_An_Web\\src\\main\\webapp\\imgProduct";

		// Đặt đường dẫn lưu hình ảnh
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
		}

		// Lưu hình ảnh vào file
		File imageFile = new File(directory, fileName);
		try (FileOutputStream fos = new FileOutputStream(imageFile)) {
			fos.write(decodedBytes);
		}

		// Trả về URL của hình ảnh đã lưu
		return "http://localhost:8080/Ecommerce_Web/imgProduct/" + fileName;
	}

	// Find Product Sku by id
	public ProductDetailResponse getSkusById(Long productId) {
		List<ProductSku> productSkus = productSkuRepository.findByProductId(productId);
		if (productSkus.isEmpty()) {
			throw new RuntimeException("Product not found by id " + productId);
		}

		Map<Long, ProductDetailResponse> productResponseMap = new HashMap<>();
		populateProductDetails(productResponseMap, productSkus);

		return productResponseMap.values().stream().findFirst().orElse(null);
	}

	// Find Product Sku by sub_category_id
	public PaginationResponse getSkusBySubCategory(String subCategoryName, MultipleOptionsProductRequest options,
			int currentPage) {
		List<Product> products = new ArrayList<>();
		int totalItems;

		// Lấy thực thể sub-category dựa trên ID
		SubCategory subCategory = subCategoryRepository.findByName(subCategoryName)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục con với ID " + subCategoryName));

		// lọc với options
		if (options != null) {
			// set options
			String gender = subCategory.getCategory().getGender().getName();
			options.setGender(gender);
			options.setSubCategory(subCategory.getName());
			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options, currentPage);
			totalItems = MultipleOptionsSQLQueryBuilder.totalProduct;
		} else {
			// Lấy danh sách sản phẩm theo danh mục con
			products = productRepository.findBySubCategory(subCategoryName, currentPage);
			totalItems = productRepository.getTotalProduct();
		}

		// Lấy danh sách ID của sản phẩm từ trang kết quả
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});
		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);

		// tính toán pagination
		int pageSize = 12;
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		PaginationResponse response = new PaginationResponse(responses, totalPages, currentPage + 1);
		return response;
	}

	// Find Product Sku by category_name
	public PaginationResponse getSkusByCategory(String categoryName, MultipleOptionsProductRequest options,
			int currentPage) {
		List<Product> products = new ArrayList<>();
		int totalItems;

		Category category = categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		// lọc với options
		if (options != null) {
			// set options
			options.setCategory(category.getName());
			options.setGender(category.getGender().getName());
			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options, currentPage);
			totalItems = MultipleOptionsSQLQueryBuilder.totalProduct;
		} else {
			products = productRepository.findByCategory(category.getId(), currentPage);
			totalItems = productRepository.getTotalProduct();
		}

		// Lấy danh sách ID của sản phẩm từ trang kết quả
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);

		// tính toán pagination
		int pageSize = 12;
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		PaginationResponse response = new PaginationResponse(responses, totalPages, currentPage + 1);
		return response;

	}

	// Find PRODUCT SKU by gender
	public PaginationResponse getSkusByGender(String gender, MultipleOptionsProductRequest options, int currentPage) {
		List<Product> products = new ArrayList<>();
		int totalItems;
		// lọc với options
		if (options != null) {
			options.setGender(gender);
			// Áp dụng lọc bằng Specification nếu có yêu cầu lọc
			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options, currentPage);
			totalItems = MultipleOptionsSQLQueryBuilder.totalProduct;
		} else {
			products = productRepository.findByGender(gender, currentPage);
			totalItems = productRepository.getTotalProduct();
		}
		// Lấy danh sách ID của sản phẩm từ trang kết quả
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);

		int pageSize = 12;
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);
		PaginationResponse response = new PaginationResponse(responses, totalPages, currentPage + 1);

		return response;

	}

	// Find PRODUCT SKU by searching
	public PaginationResponse getSkusBySearching(MultipleOptionsProductRequest options, int currentPage) {
		List<Product> products = new ArrayList<>();
		// lọc với options
		// Áp dụng lọc bằng Specification nếu có yêu cầu lọc
		products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options, currentPage);
		// Lấy danh sách ID của sản phẩm từ trang kết quả
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);

		// tính toán pagination
		int totalItems = MultipleOptionsSQLQueryBuilder.totalProduct;
		System.out.println(totalItems);
		int pageSize = 12;
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		PaginationResponse response = new PaginationResponse(responses, totalPages, currentPage + 1);
		return response;

	}

	// Get random PRODUCT SKU
	public List<ProductDetailResponse> getRandomProductSku(int numberOfRandom) {
		List<Product> products = productRepository.getRandomProduct(numberOfRandom);
		if (products.isEmpty()) {
			throw new RuntimeException("Can not get random product");
		}

		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Lấy danh sách ProductSku theo danh sách ID sản phẩm
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;

	}

	// Lấy chi tiết sản phẩm từ ProductSku và ánh xạ vào ProductDetailResponse
	private List<ProductDetailResponse> createProductDetailResponses(List<ProductSku> productSkus) {
		// Tạo bản đồ để ánh xạ chi tiết sản phẩm
		Map<Long, ProductDetailResponse> productResponseMap = new HashMap<>();
		populateProductDetails(productResponseMap, productSkus);

		// Trả về danh sách chi tiết sản phẩm
		return new ArrayList<>(productResponseMap.values());
	}

	// Điền chi tiết sản phẩm
	private void populateProductDetails(Map<Long, ProductDetailResponse> productResponseMap,
			List<ProductSku> productSkus) {
		for (ProductSku productSku : productSkus) {
			Long productId = productSku.getProductColorImage().getProduct().getId();
			ProductDetailResponse productResponse = productResponseMap.getOrDefault(productId,
					new ProductDetailResponse());

			if (productResponse.getId() == null) {
				productResponse.setId(productId);
				productResponse.setName(productSku.getProductColorImage().getProduct().getName());
				productResponse
						.setSubCategory(productSku.getProductColorImage().getProduct().getSubCategory().getName());
				productResponse.setPrice(productSku.getPrice());
				productResponse.setDescription(productSku.getProductColorImage().getProduct().getDescription());
				productResponse.setTypeProduct(productSku.getSize().getSizeType().getName());
				productResponse.setProductSkus(new ArrayList<>());
				productResponseMap.put(productId, productResponse);
			}

			processSku(productSku, productResponse);
		}
	}

	private void processSku(ProductSku productSku, ProductDetailResponse productResponse) {
		// Tìm kiếm ProductSkuResponse hiện tại dựa trên màu sắc và hình ảnh
		Optional<ProductDetailResponse.ProductSkuResponse> existingSku = productResponse.getProductSkus().stream()
				.filter(sku -> sku.getColor().equals(productSku.getProductColorImage().getColor().getName())
						&& sku.getImg().equals(productSku.getProductColorImage().getImage()))
				.findFirst();

		// Lấy số lượng tồn kho từ InventoryRepository
		Inventory inventory = inventoryRepository.findByProductSkuId(productSku.getId())
				.orElseThrow(() -> new RuntimeException("Not found inventory by id " + productSku.getId()));
		Integer stock = inventory.getStock();

		if (existingSku.isPresent()) {
			// Thêm hoặc cập nhật size và stock trong entry SKU hiện tại
			String size = productSku.getSize().getName();
			Integer currentStock = existingSku.get().getSizeAndStock().getOrDefault(size, 0);
			existingSku.get().getSizeAndStock().put(size, currentStock + stock);
		} else {
			// Tạo mới một SKU response nếu không tìm thấy màu sắc tương ứng
			ProductDetailResponse.ProductSkuResponse skuResponse = new ProductDetailResponse.ProductSkuResponse();
			skuResponse.setProductColorImgId(productSku.getProductColorImage().getId());
			skuResponse.setColor(productSku.getProductColorImage().getColor().getName());
			skuResponse.setImg(productSku.getProductColorImage().getImage());

			// Khởi tạo map sizeAndStock với size hiện tại và số lượng tồn kho
			Map<String, Integer> sizeAndStock = new HashMap<>();
			sizeAndStock.put(productSku.getSize().getName(), stock);
			skuResponse.setSizeAndStock(sizeAndStock);

			// Thêm SKU mới vào danh sách SKUs của product
			productResponse.getProductSkus().add(skuResponse);
		}
	}

	public static void main(String[] args) {
		new ProductService().deleteProduct(36L, null, null);
	}

}

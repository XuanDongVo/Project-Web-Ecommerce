package service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dto.request.MultipleOptionsProductRequest;
import dto.respository.ProductDetailResponse;
import entity.Category;
import entity.Inventory;
import entity.Product;
import entity.ProductSku;
import entity.SubCategory;
import repository.category.CategoryRepository;
import repository.category.SubCategoryRepository;
import repository.color.ColorRepository;
import repository.product.ProductColorImgRepository;
import repository.product.ProductRepository;
import repository.product.ProductSkuRepository;
import repository.size.SizeRepository;
import utils.MultipleOptionsSQLQueryBuilder;

public class ProductService {

	private ProductSkuRepository productSkuRepository = new ProductSkuRepository();
	private ProductRepository productRepository = new ProductRepository();
	private SubCategoryRepository subCategoryRepository = new SubCategoryRepository();
	private CategoryRepository categoryRepository = new CategoryRepository();
	private ColorRepository colorRepository = new ColorRepository();
	private ProductColorImgRepository productColorImgRepository = new ProductColorImgRepository();
	private SizeRepository sizeRepository = new SizeRepository();

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
	public List<ProductDetailResponse> getSkusBySubCategory(String subCategoryName,
			MultipleOptionsProductRequest options) {
		List<Product> products = new ArrayList<>();

		// Láº¥y thá»±c thá»ƒ sub-category dá»±a trĂªn ID
		SubCategory subCategory = subCategoryRepository.findByName(subCategoryName).orElseThrow(
				() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y danh má»¥c con vá»›i ID " + subCategoryName));

		// lá»�c vá»›i options
		if (options != null) {
			// set options
			String gender = subCategory.getCategory().getGender().getName();
			options.setGender(gender);
			options.setSubCategory(subCategory.getName());
			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options);
		} else {
			// Láº¥y danh sĂ¡ch sáº£n pháº©m theo danh má»¥c con
			products = productRepository.findBySubCategory(subCategoryName);
		}

		// Láº¥y danh sĂ¡ch ID cá»§a sáº£n pháº©m tá»« trang káº¿t quáº£
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Láº¥y danh sĂ¡ch ProductSku theo danh sĂ¡ch ID sáº£n pháº©m
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});
		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;
	}

	// Find Product Sku by category_name
	public List<ProductDetailResponse> getSkusByCategory(String categoryName, MultipleOptionsProductRequest options) {
		List<Product> products = new ArrayList<>();

		Category category = categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		// lá»�c vá»›i options
		if (options != null) {
			// set options
			options.setCategory(category.getName());
			options.setGender(category.getGender().getName());

			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options);
		} else {
			products = productRepository.findByCategory(category.getId());
		}

		// Láº¥y danh sĂ¡ch ID cá»§a sáº£n pháº©m tá»« trang káº¿t quáº£
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Láº¥y danh sĂ¡ch ProductSku theo danh sĂ¡ch ID sáº£n pháº©m
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;

	}

	// Find PRODUCT SKU by gender
	public List<ProductDetailResponse> getSkusByGender(String gender, MultipleOptionsProductRequest options) {
		List<Product> products = new ArrayList<>();
		// lá»�c vá»›i options
		if (options != null) {
			options.setGender(gender);
			// Ă�p dá»¥ng lá»�c báº±ng Specification náº¿u cĂ³ yĂªu cáº§u lá»�c
			products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options);
		} else {
			products = productRepository.findByGender(gender);
		}
		// Láº¥y danh sĂ¡ch ID cá»§a sáº£n pháº©m tá»« trang káº¿t quáº£
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Láº¥y danh sĂ¡ch ProductSku theo danh sĂ¡ch ID sáº£n pháº©m
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;

	}

	// Find PRODUCT SKU by searching
	public List<ProductDetailResponse> getSkusBySearching(MultipleOptionsProductRequest options) {
		List<Product> products = new ArrayList<>();
		// lá»�c vá»›i options
		// Ă�p dá»¥ng lá»�c báº±ng Specification náº¿u cĂ³ yĂªu cáº§u lá»�c
		products = MultipleOptionsSQLQueryBuilder.findByMultipleOptions(options);
		// Láº¥y danh sĂ¡ch ID cá»§a sáº£n pháº©m tá»« trang káº¿t quáº£
		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Láº¥y danh sĂ¡ch ProductSku theo danh sĂ¡ch ID sáº£n pháº©m
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;

	}

	// Get random PRODUCT SKU
	public List<ProductDetailResponse> getRandomProductSku(int numberOfRandom) {
		List<Product> products = productRepository.getRandomProduct(numberOfRandom);
		if (products.isEmpty()) {
			throw new RuntimeException("Can not get random product");
		}

		List<Long> productIds = products.stream().map(Product::getId).toList();

		// Láº¥y danh sĂ¡ch ProductSku theo danh sĂ¡ch ID sáº£n pháº©m
		List<ProductSku> productSkuList = new ArrayList<>();
		productIds.forEach(productId -> {
			List<ProductSku> productskus = productSkuRepository.findByProductId(productId);
			productSkuList.addAll(productskus);
		});

		List<ProductDetailResponse> responses = createProductDetailResponses(productSkuList);
		return responses;

	}

	// Láº¥y chi tiáº¿t sáº£n pháº©m tá»« ProductSku vĂ  Ă¡nh xáº¡ vĂ o
	// ProductDetailResponse
	private List<ProductDetailResponse> createProductDetailResponses(List<ProductSku> productSkus) {
		// Táº¡o báº£n Ä‘á»“ Ä‘á»ƒ Ă¡nh xáº¡ chi tiáº¿t sáº£n pháº©m
		Map<Long, ProductDetailResponse> productResponseMap = new HashMap<>();
		populateProductDetails(productResponseMap, productSkus);

		// Tráº£ vá»� danh sĂ¡ch chi tiáº¿t sáº£n pháº©m
		return new ArrayList<>(productResponseMap.values());
	}

	// Ä�iá»�n chi tiáº¿t sáº£n pháº©m
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
				productResponse.setTypeProduct(productSku.getSize().getSizeType().getName());
				productResponse.setProductSkus(new ArrayList<>());
				productResponseMap.put(productId, productResponse);
			}

			processSku(productSku, productResponse);
		}
	}

	private void processSku(ProductSku productSku, ProductDetailResponse productResponse) {
		// TĂ¬m kiáº¿m ProductSkuResponse hiá»‡n táº¡i dá»±a trĂªn mĂ u sáº¯c vĂ  hĂ¬nh
		// áº£nh
		Optional<ProductDetailResponse.ProductSkuResponse> existingSku = productResponse.getProductSkus().stream()
				.filter(sku -> sku.getColor().equals(productSku.getProductColorImage().getColor().getName())
						&& sku.getImg().equals(productSku.getProductColorImage().getImage()))
				.findFirst();

		// Láº¥y sá»‘ lÆ°á»£ng tá»“n kho tá»« InventoryRepository
		Inventory inventory = inventoryRepository.findByProductSkuId(productSku.getId())
				.orElseThrow(() -> new RuntimeException("Not found inventory by id " + productSku.getId()));
		Integer stock = inventory.getStock();

		if (existingSku.isPresent()) {
			// ThĂªm hoáº·c cáº­p nháº­t size vĂ  stock trong entry SKU hiá»‡n táº¡i
			String size = productSku.getSize().getName();
			Integer currentStock = existingSku.get().getSizeAndStock().getOrDefault(size, 0);
			existingSku.get().getSizeAndStock().put(size, currentStock + stock);
		} else {
			// Táº¡o má»›i má»™t SKU response náº¿u khĂ´ng tĂ¬m tháº¥y mĂ u sáº¯c tÆ°Æ¡ng
			// á»©ng
			ProductDetailResponse.ProductSkuResponse skuResponse = new ProductDetailResponse.ProductSkuResponse();
			skuResponse.setProductColorImgId(productSku.getProductColorImage().getId());
			skuResponse.setColor(productSku.getProductColorImage().getColor().getName());
			skuResponse.setImg(productSku.getProductColorImage().getImage());

			// Khá»Ÿi táº¡o map sizeAndStock vá»›i size hiá»‡n táº¡i vĂ  sá»‘ lÆ°á»£ng tá»“n
			// kho
			Map<String, Integer> sizeAndStock = new HashMap<>();
			sizeAndStock.put(productSku.getSize().getName(), stock);
			skuResponse.setSizeAndStock(sizeAndStock);

			// ThĂªm SKU má»›i vĂ o danh sĂ¡ch SKUs cá»§a product
			productResponse.getProductSkus().add(skuResponse);
		}
	}



}

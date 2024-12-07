package repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import dbConnection.DBConnection;
import entity.Color;
import entity.Product;
import entity.ProductColorImage;
import entity.ProductSku;
import entity.Size;
import entity.SizeType;
import entity.SubCategory;

public class ProductSkuRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	
	public void removeByProductSkuId( Connection connection,Long productskuId) {
		try {
			String sql = "DELETE FROM product_sku WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, productskuId);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public long addProductSku(Connection connection, ProductSku productSku) throws SQLException {
		long productSkuId = 0;
		String sql = "INSERT INTO product_sku (product_color_img_id, size_id, price) VALUES (?, ?, ?)";
		try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setLong(1, productSku.getProductColorImage().getId());
			pst.setLong(2, productSku.getSize().getId());
			pst.setDouble(3, productSku.getPrice());
			// Thực thi câu lệnh
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					productSkuId = rs.getLong(1); // Lấy ID của đơn hàng vừa tạo
				}
			}
		}

		return productSkuId;
	}

	public ProductSku findById(Long id) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT pu.id, pu.price, pci.image , pci.id, c.name as color_name,p.id as product_id ,p.name as product_name, "
					+ "sc.name as subcategory_name, s.name as size_name, st.name as type_product "
					+ "FROM product_sku pu " + "INNER JOIN product_color_img pci ON pu.product_color_img_id = pci.id "
					+ "INNER JOIN product p ON pci.product_id = p.id " + "INNER JOIN color c ON c.id = pci.color_id "
					+ "INNER JOIN sub_category sc ON sc.id = p.sub_category_id "
					+ "INNER JOIN size s ON s.id = pu.size_id " + "INNER JOIN size_type st ON s.size_type_id = st.id "
					+ "WHERE pu.id = ? ";

			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet resultSet = pst.executeQuery();

			if (resultSet.next()) { // Correct traversal of result set
				ProductSku productSku = new ProductSku();

				// Set ProductSku fields
				productSku.setId(resultSet.getLong("pu.id"));
				productSku.setPrice(resultSet.getDouble("pu.price"));

				// Set ProductColorImage with product and color information
				ProductColorImage productColorImage = new ProductColorImage();
				productColorImage.setId(resultSet.getLong("pci.id"));
				productColorImage.setImage(resultSet.getString("pci.image"));

				// Create Product and SubCategory objects
				Product product = new Product();
				product.setId(resultSet.getLong("product_id"));
				product.setName(resultSet.getString("product_name"));
				SubCategory subCategory = new SubCategory();
				subCategory.setName(resultSet.getString("subcategory_name"));
				product.setSubCategory(subCategory);
				productColorImage.setProduct(product);

				// Create Color object
				Color color = new Color();
				color.setName(resultSet.getString("color_name"));
				productColorImage.setColor(color);

				// Set productColorImage to productSku
				productSku.setProductColorImage(productColorImage);

				// Set Size object
				Size size = new Size();
				size.setName(resultSet.getString("size_name"));
				productSku.setSize(size);

				SizeType sizeType = new SizeType();
				sizeType.setName(resultSet.getString("type_product"));
				size.setSizeType(sizeType);
				return productSku;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// Find product SKUs by product ID
	public List<ProductSku> findByProductId(Long id) {
		connection = DBConnection.getConection();
		String sql = "";

		List<ProductSku> list = new ArrayList<>();
		try {
			sql = "SELECT pu.id, pu.price, pci.image , pci.id, c.name as color_name,p.id as product_id ,p.name as product_name, "
					+ "sc.name as subcategory_name, s.name as size_name, st.name as type_product "
					+ "FROM product_sku pu " + "INNER JOIN product_color_img pci ON pu.product_color_img_id = pci.id "
					+ "INNER JOIN product p ON pci.product_id = p.id " + "INNER JOIN color c ON c.id = pci.color_id "
					+ "INNER JOIN sub_category sc ON sc.id = p.sub_category_id "
					+ "INNER JOIN size s ON s.id = pu.size_id " + "INNER JOIN size_type st ON s.size_type_id = st.id "
					+ "WHERE p.id = ? ";

			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet resultSet = pst.executeQuery();

			while (resultSet.next()) { // Correct traversal of result set
				ProductSku productSku = new ProductSku();

				// Set ProductSku fields
				productSku.setId(resultSet.getLong("pu.id"));
				productSku.setPrice(resultSet.getDouble("pu.price"));

				// Set ProductColorImage with product and color information
				ProductColorImage productColorImage = new ProductColorImage();
				productColorImage.setId(resultSet.getLong("pci.id"));
				productColorImage.setImage(resultSet.getString("pci.image"));
				;
				// Create Product and SubCategory objects
				Product product = new Product();
				product.setId(resultSet.getLong("product_id"));
				product.setName(resultSet.getString("product_name"));
				SubCategory subCategory = new SubCategory();
				subCategory.setName(resultSet.getString("subcategory_name"));
				product.setSubCategory(subCategory);
				productColorImage.setProduct(product);

				// Create Color object
				Color color = new Color();
				color.setName(resultSet.getString("color_name"));
				productColorImage.setColor(color);

				// Set productColorImage to productSku
				productSku.setProductColorImage(productColorImage);

				// Set Size object
				Size size = new Size();
				size.setName(resultSet.getString("size_name"));
				productSku.setSize(size);

				SizeType sizeType = new SizeType();
				sizeType.setName(resultSet.getString("type_product"));
				size.setSizeType(sizeType);

				// Add productSku to the list
				list.add(productSku);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public Optional<ProductSku> findByProductColorImgAndSize(Long productColorImgId, String size) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT sku.id , p.name , pci.image, color.name as color, size.name as size, sku.price FROM product_sku AS sku\r\n"
					+ "	INNER JOIN product_color_img AS pci ON pci.id = sku.product_color_img_id\r\n"
					+ " INNER JOIN color ON color.id  = pci.color_id\r\n"
					+ " INNER JOIN product AS p ON p.id = pci.product_id\r\n"
					+ "	INNER JOIN size ON size.id = sku.size_id where size.name = ?  AND pci.id = ? ";
			pst = connection.prepareStatement(sql);
			pst.setString(1, size);
			pst.setLong(2, productColorImgId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				ProductSku productSku = new ProductSku(rs.getLong(1), null, null, (rs.getDouble(6)));
				ProductColorImage productColorImage = new ProductColorImage();
				productColorImage.setImage(rs.getString("pci.image"));

				Color color = new Color();
				color.setName(rs.getString("color"));
				productColorImage.setColor(color);

				Size currentSize = new Size();
				currentSize.setName(rs.getString("size"));
				productSku.setProductColorImage(productColorImage);
				productSku.setSize(currentSize);

				Product product = new Product();
				product.setName(rs.getString("name"));
				productColorImage.setProduct(product);

				return Optional.of(productSku);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// Phương thức chuyển trạng thái Commit khi xử lý xong OrderDetail
	public void finalizeTransaction() {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.commit(); // Commit giao dịch
				connection.setAutoCommit(true); // Trả lại trạng thái AutoCommit
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	// Phương thức rollback giao dịch
	public void rollbackTransaction() {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	// Phương thức hỗ trợ đóng các tài nguyên (Connection, PreparedStatement,
	// ResultSet)
	private void closeResources() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

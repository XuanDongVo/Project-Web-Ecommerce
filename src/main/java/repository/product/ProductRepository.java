package repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import dto.response.ProductDetailResponse;
import entity.Product;
import entity.SubCategory;
import repository.category.SubCategoryRepository;

public class ProductRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	private int totalProduct = 0;

	// lay ra toan bo danh sach san pham
	public List<Product> getAllProduct() {
		List<Product> list = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM product";
		try {
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getString(2), rs.getString(3), null);
				list.add(product);
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
					DBConnection.closeConnection(connection);
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public int updateProduct(Connection connection, ProductDetailResponse detailResponse, SubCategory subCategory)
			throws SQLException {
		String sql = "UPDATE product SET name = ?, description = ?  , sub_category_id = ?  WHERE id = ?";
		pst = connection.prepareStatement(sql);
		pst.setString(1, detailResponse.getName());
		pst.setString(2, detailResponse.getDescription());
		pst.setLong(3, subCategory.getId());
		pst.setLong(4, detailResponse.getId());

		return pst.executeUpdate();
	}

	public void removeById(Connection connection, Long productId) {
		try {
			String sql = "DELETE FROM product WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, productId);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long addProduct(Connection connection, Product product) throws SQLException {
		long productId = 0;
		String sql = "INSERT INTO product (name, description, sub_category_id) VALUES (?, ?, ?)";
		try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, product.getName());
			pst.setString(2, product.getDescription());
			pst.setLong(3, product.getSubCategory().getId());
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					if (rs.next()) {
						productId = rs.getLong(1);
					}
				}
			}
		}
		return productId;
	}

	public List<Product> getRandomProduct(int randomOfProduct) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product WHERE id > FLOOR(RAND() * (SELECT MAX(id) FROM product)) LIMIT  ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setInt(1, randomOfProduct);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getString(2), rs.getString(3), null);
				products.add(product);
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
					DBConnection.closeConnection(connection);
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by sub_cateogry ID
	public List<Product> findBySubCategory(String subCategory, int currentPage) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";

		int itemsPerPage = 12;
		int offset = (currentPage) * itemsPerPage;
		try {
			// Lấy tổng số sản phẩm
			sql = "SELECT COUNT(*) " + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "WHERE sc.name = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, subCategory);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				totalProduct = rs.getInt(1);
			}

			sql = "SELECT product.*" + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "WHERE sc.name = ? " + "LIMIT ? OFFSET ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, subCategory);
			pst.setInt(2, itemsPerPage);
			pst.setInt(3, offset);
			rs = pst.executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getString(2), rs.getString(3), null);
				products.add(product);
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
					DBConnection.closeConnection(connection);
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by Cateogry ID
	public List<Product> findByCategory(Long id, int currentPage) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";

		int itemsPerPage = 12;
		int offset = (currentPage) * itemsPerPage;

		try {
			sql = "SELECT COUNT(*) " + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "INNER JOIN category AS c " + "ON c.id = sc.category_id "
					+ "WHERE c.id = ?";

			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				totalProduct = rs.getInt(1); // Lưu tổng số sản phẩm vào biến totalProduct
			}

			sql = "SELECT product.*" + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "INNER JOIN category AS c " + "ON c.id = sc.category_id "
					+ "WHERE c.id = ? " + "LIMIT ? OFFSET ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);
			pst.setInt(2, itemsPerPage);
			pst.setInt(3, offset);
			rs = pst.executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getString(2), rs.getString(3), null);
				products.add(product);
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
					DBConnection.closeConnection(connection);
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by GENDER NAME
	public List<Product> findByGender(String gender, int currentPage) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";

		int itemsPerPage = 12;
		int offset = (currentPage) * itemsPerPage;

		try {
			// Lấy tổng số sản phẩm
			String countSql = "SELECT COUNT(*) FROM ecommerce.product AS p "
					+ "INNER JOIN sub_category AS sc ON sc.id = p.sub_category_id "
					+ "INNER JOIN category AS c ON c.id = sc.category_id "
					+ "INNER JOIN gender ON gender.id = c.gender_id " + "WHERE gender.name = ?";

			pst = connection.prepareStatement(countSql);
			pst.setString(1, gender);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				totalProduct = rs.getInt(1); // Lưu tổng số sản phẩm vào biến totalProduct
			}

			// Lấy sản phẩm theo phân trang
			sql = "SELECT p.* FROM ecommerce.product AS p "
					+ "INNER JOIN sub_category AS sc ON sc.id = p.sub_category_id "
					+ "INNER JOIN category AS c ON c.id = sc.category_id "
					+ "INNER JOIN gender ON gender.id = c.gender_id " + "WHERE gender.name = ? " + "LIMIT ? OFFSET ?";

			pst = connection.prepareStatement(sql);
			pst.setString(1, gender);
			pst.setInt(2, itemsPerPage);
			pst.setInt(3, offset);
			rs = pst.executeQuery();

			while (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getString(2), rs.getString(3), null);
				products.add(product);
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
					DBConnection.closeConnection(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return products;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

}

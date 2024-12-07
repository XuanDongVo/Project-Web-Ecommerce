package repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import entity.Product;

public class ProductRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

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
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
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
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by sub_cateogry ID
	public List<Product> findBySubCategory(String subCategory) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";
		try {
			sql = "SELECT product.*" + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "WHERE sc.name = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, subCategory);
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
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by Cateogry ID
	public List<Product> findByCategory(Long id) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";
		try {
			sql = "SELECT product.*" + "FROM ecommerce.product " + "INNER JOIN sub_category AS sc "
					+ "ON sc.id = product.sub_category_id " + "INNER JOIN category AS c " + "ON c.id = sc.category_id "
					+ "WHERE c.id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);
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
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	// Find product by GENDER NAME
	public List<Product> findByGender(String gender) {
		connection = DBConnection.getConection();
		List<Product> products = new ArrayList<>();
		String sql = "";
		try {
			sql = "	SELECT p.* FROM ecommerce.product as p "
					+ "	INNER JOIN sub_category AS sc ON sc.id = p.sub_category_id "
					+ "	INNER JOIN category AS c ON c.id = sc.category_id "
					+ "	INNER JOIN gender ON gender.id = c.gender_id " + "	WHERE gender.name = ? ";
			pst = connection.prepareStatement(sql);
			pst.setString(1, gender);
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
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

}

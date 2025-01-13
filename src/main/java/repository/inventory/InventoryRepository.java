package repository.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dbConnection.DBConnection;
import dto.response.AdminInventory;
import entity.Inventory;
import entity.Product;
import entity.ProductColorImage;
import entity.ProductSku;

public class InventoryRepository {
	public Connection connection = null;
	public PreparedStatement preparedStatement = null;

	public void removeByProductSkuId(Connection connection, Long productskuId) {
		try {
			String sql = "DELETE FROM inventory WHERE product_sku_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, productskuId);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStockInInventory(Connection connection, Inventory inventory) {
		String sql = "INSERT INTO inventory (product_sku_id, stock) VALUES (?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, inventory.getProductSku().getId());
			preparedStatement.setInt(2, inventory.getStock());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Optional<Inventory> findByProductSkuId(Long id) {
		Connection connection = DBConnection.getConection();
		String sql = "SELECT i.id, i.stock, p.name FROM ecommerce.inventory i "
				+ "INNER JOIN product_sku pu ON pu.id = i.product_sku_id "
				+ "INNER JOIN product_color_img pci ON pu.product_color_img_id = pci.id "
				+ "INNER JOIN product p ON pci.product_id = p.id " + "WHERE i.product_sku_id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) { // Kiểm tra nếu có kết quả
					Long inventoryId = resultSet.getLong("id");
					int stock = resultSet.getInt("stock");
					String productName = resultSet.getString("name");
					ProductSku productSku = new ProductSku();

					ProductColorImage productColorImage = new ProductColorImage();
					productSku.setProductColorImage(productColorImage);

					Product product = new Product();
					product.setName(productName);
					productColorImage.setProduct(product);

					Inventory inventory = new Inventory(inventoryId, productSku, stock);
					return Optional.of(inventory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return Optional.empty(); // Trả về Optional rỗng nếu không có kết quả
	}

	public Optional<Inventory> findByProductName(String name) {
		Connection connection = DBConnection.getConection();
		String sql = "SELECT i.id, i.stock, p.name FROM ecommerce.inventory i "
				+ "INNER JOIN product_sku pu ON pu.id = i.product_sku_id "
				+ "INNER JOIN product_color_img pci ON pu.product_color_img_id = pci.id "
				+ "INNER JOIN product p ON pci.product_id = p.id " + "WHERE p.name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, name);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) { // Kiểm tra nếu có kết quả
					Long inventoryId = resultSet.getLong("id");
					int stock = resultSet.getInt("stock");
					String productName = resultSet.getString("name");
					ProductSku productSku = new ProductSku();

					ProductColorImage productColorImage = new ProductColorImage();
					productSku.setProductColorImage(productColorImage);

					Product product = new Product();
					product.setName(productName);
					productColorImage.setProduct(product);

					Inventory inventory = new Inventory(inventoryId, productSku, stock);
					return Optional.of(inventory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return Optional.empty(); // Trả về Optional rỗng nếu không có kết quả
	}

	public void updateQuantityByInvetoryid(Connection connection, Long id, int quantity) {
		try {
			String sql = "UPDATE inventory SET stock = ? WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setLong(2, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AdminInventory> getAllInventory() {
		List<AdminInventory> list = new ArrayList<AdminInventory>();
		connection = DBConnection.getConection();
		String sql = "select i.id, p.name, c.name, s.name, ps.price, i.stock " + " from inventory i "
				+ " inner join product_sku ps on ps.id = i.product_sku_id "
				+ " inner join  product_color_img pci on pci.id = ps.product_color_img_id "
				+ " inner join product p on p.id = pci.product_id " + " inner join color c on c.id = pci.color_id "
				+ " inner join size s on s.id = ps.size_id";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					list.add(new AdminInventory(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getDouble(5), rs.getInt(6)));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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

	public List<ProductSku> getAllProductSku() {
		List<ProductSku> list = new ArrayList<ProductSku>();
		connection = DBConnection.getConection();
		String sql = "select id from ecommerce.product_sku";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					list.add(new ProductSku(rs.getLong(1)));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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

	public boolean addInventory(long skuId, int stock) {
		connection = DBConnection.getConection();
		String sql = "INSERT INTO inventory (product_sku_id, stock) VALUES (?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, skuId);
			preparedStatement.setInt(2, stock);
			int row = preparedStatement.executeUpdate();
			if (row > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkInventoryBySkuId(Long skuId) {
		connection = DBConnection.getConection();
		String sql = "select * from inventory where product_sku_id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, skuId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteInventoryByInventoryId(Long inventoryId) {
		connection = DBConnection.getConection();
		String sql = "delete from inventory where id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, inventoryId);
			int row = preparedStatement.executeUpdate();
			if (row > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateInventoryByInventoryId(Long inventoryId, int stock) {
		connection = DBConnection.getConection();
		String sql = "update inventory set stock = ? where id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, stock);
			preparedStatement.setLong(2, inventoryId);
			int row = preparedStatement.executeUpdate();
			if (row > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


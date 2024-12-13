package repository.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import dbConnection.DBConnection;
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
		}
		return Optional.empty(); // Trả về Optional rỗng nếu không có kết quả
	}

	public void updateQuantityByInvetoryid(Long id, int quantity) {
		Connection connection = DBConnection.getConection();
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

}

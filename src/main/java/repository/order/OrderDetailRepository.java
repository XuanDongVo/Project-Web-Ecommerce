package repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dbConnection.DBConnection;
import entity.OrderDetail;

public class OrderDetailRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	// lưu giá trị orderdetail vào database
	public void save(OrderDetail orderDetail) {
		connection = DBConnection.getConection();
		try {
			String sql = "INSERT INTO order_detail (product_sku_id, price, quantity) VALUES (?, ?, ?)";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, orderDetail.getProductSkuId());
			pst.setDouble(2, orderDetail.getPrice());
			pst.setInt(3, orderDetail.getQuantity());

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeByProductSkuId(Connection connection, Long ProductSkuId) {
		try {
			String sql = "DELETE FROM order_detail WHERE product_sku_id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, ProductSkuId);
			pst.executeUpdate();
		} catch (Exception e) {
		}
	}
}

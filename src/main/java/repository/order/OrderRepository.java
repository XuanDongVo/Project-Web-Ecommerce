package repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnection.DBConnection;
import dto.request.OrderRequest;
import entity.User;
import utils.OrderStatus;

public class OrderRepository {
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	// Phương thức tạo đơn hàng mới
	public long createOrder(Connection connection, OrderRequest orderRequest, User user) {
		long orderId = 0;
		try {
			// Đặt AutoCommit thành false
			connection.setAutoCommit(false);

			// Câu lệnh SQL để chèn dữ liệu vào bảng `order`
			String sql = "INSERT INTO `order` (user_id, order_status, total_price, create_at, customer_email, customer_name, customer_phone, customer_address) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			// Kiểm tra nếu người dùng tồn tại, sử dụng ID người dùng; nếu không, đặt giá
			// trị NULL cho user_id
			if (user != null && user.getId() != null) {
				pst.setLong(1, user.getId());
			} else {
				pst.setNull(1, java.sql.Types.NULL);
			}

			// Đặt các thông tin khác của đơn hàng
			pst.setString(2, OrderStatus.PENDING.toString());
			pst.setDouble(3, orderRequest.getTotalPrice());
			pst.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
			pst.setString(5, orderRequest.getCustomerEmail());
			pst.setString(6, orderRequest.getCustomerName());
			pst.setString(7, orderRequest.getCustomerPhone());
			pst.setString(8, orderRequest.getAddress());

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					orderId = rs.getLong(1); // Lấy ID của đơn hàng vừa tạo
				}
			}

			// Nếu không tạo được đơn hàng, ném ra ngoại lệ SQLException
			if (orderId == 0) {
				throw new SQLException("Không thể tạo đơn hàng: Không có ID đơn hàng được sinh ra.");
			}

		} catch (SQLException e) {
			rollbackTransaction(connection);
			e.printStackTrace();
		}
		return orderId; // Trả về ID của đơn hàng vừa tạo
	}

	// Phương thức chuyển trạng thái Commit khi xử lý xong OrderDetail
	public void finalizeTransaction(Connection connection) {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.commit(); // Commit giao dịch
				connection.setAutoCommit(true); // Trả lại trạng thái AutoCommit
				DBConnection.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Phương thức rollback giao dịch
	public void rollbackTransaction(Connection connection) {
		try {
			if (connection != null && !connection.getAutoCommit()) {
				connection.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

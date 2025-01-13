package repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import dto.request.OrderRequest;
import dto.response.AdminOrderResponse;
import entity.User;
import utils.OrderStatus;

public class OrderRepository {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private Connection connection;
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
			pst.setString(2, OrderStatus.ĐANG_CHỜ.toString());
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

	public List<AdminOrderResponse> getAllOrderWithResponse() {
		List<AdminOrderResponse> list = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT o.id, o.customer_name, p.name , od.quantity, o.total_price, o.order_status "
				+ " FROM ecommerce.order o "
				+ " INNER JOIN order_detail od ON od.order_id = o.id "
				+ " INNER JOIN product_sku ps ON ps.id = od.product_sku_id "
				+ " INNER JOIN product_color_img pci ON pci.id = ps.product_color_img_id "
				+ " INNER JOIN product p ON p.id = pci.product_id";
		try {
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				AdminOrderResponse aor = new AdminOrderResponse(rs.getLong(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getDouble(5), rs.getString(6));
				list.add(aor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnection(connection);
		}
		return list;
	}

	 public boolean deleteOrder(Long orderId) {
	        String deleteOrderDetailQuery = "DELETE FROM order_detail WHERE order_id = ?";
	        String deleteOrderQuery = "DELETE FROM ecommerce.order WHERE id = ?";
	        connection = DBConnection.getConection();
	        try (PreparedStatement orderDetailStmt = connection.prepareStatement(deleteOrderDetailQuery);
	             PreparedStatement orderStmt = connection.prepareStatement(deleteOrderQuery)) {

	            // Xóa các chi tiết đơn hàng (order_detail)
	            orderDetailStmt.setLong(1, orderId);
	            orderDetailStmt.executeUpdate();

	            // Xóa đơn hàng (orders)
	            orderStmt.setLong(1, orderId);
	            int rowsAffected = orderStmt.executeUpdate();

	            // Kiểm tra nếu xóa thành công
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }finally {
				DBConnection.closeConnection(connection);
			}
	    }

	 public AdminOrderResponse getOrderById(Long orderId) {
		    AdminOrderResponse orderResponse = null;
		    String sql = "SELECT o.id, o.customer_name AS user_name, p.name AS product_name, od.quantity, "
		               + "o.total_price, o.order_status "
		               + "FROM ecommerce.order o "
		               + "INNER JOIN order_detail od ON od.order_id = o.id "
		               + "INNER JOIN product_sku ps ON ps.id = od.product_sku_id "
		               + "INNER JOIN product_color_img pci ON pci.id = ps.product_color_img_id "
		               + "INNER JOIN product p ON p.id = pci.product_id "
		               + "WHERE o.id = ?";

		    try (Connection connection = DBConnection.getConection();
		         PreparedStatement pst = connection.prepareStatement(sql)) {
		         
		        pst.setLong(1, orderId); // Gán tham số orderId vào câu truy vấn
		        try (ResultSet rs = pst.executeQuery()) {
		            if (rs.next()) {
		                orderResponse = new AdminOrderResponse(
		                    rs.getLong(1),
		                    rs.getString(2),
		                    rs.getString(3),
		                    rs.getInt(4),
		                    rs.getDouble(5),
		                    rs.getString(6)
		                );
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }finally {
				DBConnection.closeConnection(connection);
			}
		    
		    return orderResponse; // Trả về kết quả
		}
	 
	 public void updateOrderById(Long orderId, String status) {
		    String sql = "UPDATE ecommerce.order SET order_status = ? WHERE id = ?";

		    try (Connection connection = DBConnection.getConection();
		         PreparedStatement pst = connection.prepareStatement(sql)) {

		        // Gán giá trị cho câu lệnh SQL
		        pst.setString(1, status);  // Cập nhật trạng thái đơn hàng
		        pst.setLong(2, orderId);    // Cập nhật theo id đơn hàng

		        // Thực thi câu lệnh UPDATE
		        int rowsAffected = pst.executeUpdate(); // Dùng executeUpdate thay vì executeQuery

		        // Kiểm tra nếu có dòng bị ảnh hưởng
		        if (rowsAffected > 0) {
		            System.out.println("Đơn hàng đã được cập nhật thành công.");
		        } else {
		            System.out.println("Không tìm thấy đơn hàng với ID: " + orderId);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }finally {
				DBConnection.closeConnection(connection);
			}
		}
	 
	 public String getEmailByOrderId(Long idConvert) {
		 String res = null;
		 String sql = "select o.customer_email "
		 		+ " from ecommerce.order o "
		 		+ "where  id = ?";

	    try (Connection connection = DBConnection.getConection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	         
	        pst.setLong(1, idConvert); // Gán tham số orderId vào câu truy vấn
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                res = rs.getString(1);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        res = null;
	    }
	    return res;
		}
}

	 

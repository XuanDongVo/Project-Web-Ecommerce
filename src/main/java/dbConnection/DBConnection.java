package dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {
	private static HikariDataSource dataSource;

	// Khởi tạo Connection Pool
	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/ecommerce");
		config.setUsername("root");
		config.setPassword("dong14052004");

		// Các cấu hình tối ưu hóa HikariCP
		config.setMaximumPoolSize(90); // Số kết nối tối đa trong pool
		config.setMinimumIdle(2); // Số kết nối tối thiểu trong trạng thái rảnh
		config.setIdleTimeout(30000); // Thời gian rảnh tối đa trước khi bị loại bỏ
		config.setMaxLifetime(1800000); // Thời gian sống tối đa của một kết nối
		config.setConnectionTimeout(20000); // Thời gian chờ tối đa để lấy kết nối
		 config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource = new HikariDataSource(config);
	}

	// Phương thức lấy kết nối
	public static Connection getConection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("Error getting connection: " + e.getMessage());
			return null;
		}
	}

	// Đóng kết nối (tự động trả về pool)
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close(); // Trả về pool, không phải thực sự đóng
			}
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e.getMessage());
		}
	}

}

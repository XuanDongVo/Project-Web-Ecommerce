package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConection() {
		final String url = "jdbc:mysql://localhost:3306/ecommerce";
		final String userName = "root";
		final String passwd = "dong14052004";

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, passwd);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public void closeConection(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
	}
}

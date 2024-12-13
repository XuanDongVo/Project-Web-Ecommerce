package repository.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import dbConnection.DBConnection;
import entity.Cart;
import entity.Product;

public class CartRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public Optional<Cart> getUserCartByPhone(String phone) {
		connection = DBConnection.getConection();
		String sql = "";
		try {
			sql = "SELECT cart.* FROM cart " + "JOIN user ON user.id = cart.user_id " + "WHERE user.phone = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, phone);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Cart cart = new Cart(rs.getLong(1), null);
				return Optional.of(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

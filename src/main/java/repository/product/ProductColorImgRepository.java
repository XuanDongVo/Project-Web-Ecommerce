package repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DBConnection;
import entity.Color;
import entity.Product;
import entity.ProductColorImage;

public class ProductColorImgRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	public long addProductColorImg(Connection connection, ProductColorImage productColorImage) throws SQLException {
		long productColorImgId = 0;
		String sql = "INSERT INTO product_color_img (product_id, color_id, image) VALUES (?, ?, ?)";
		try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pst.setLong(1, productColorImage.getProduct().getId());
			pst.setLong(2, productColorImage.getColor().getId());
			pst.setString(3, productColorImage.getImage());
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					if (rs.next()) {
						productColorImgId = rs.getLong(1);
					}
				}
			}
		}
		return productColorImgId;
	}

	public void removeByProductId( Connection connection,Long productId) {
		try {
			String sql = "DELETE FROM product_color_img WHERE product_id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, productId);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

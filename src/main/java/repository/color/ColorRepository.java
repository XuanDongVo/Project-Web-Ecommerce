package repository.color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import entity.Color;

public class ColorRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public List<Color> getAllColor() {
		List<Color> colors = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM color";
		try {
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Color color = new Color(rs.getLong(1), rs.getString(2), rs.getString(3));
				colors.add(color);
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
		return colors;
	}

	public Color findByName(String name) {
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM color where name = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return new Color(rs.getLong(1), rs.getString(2), rs.getString(3));
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
		return null;
	}
}

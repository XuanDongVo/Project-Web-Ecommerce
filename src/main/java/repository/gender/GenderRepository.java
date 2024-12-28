package repository.gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import entity.Gender;

public class GenderRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public List<Gender> getAllGender() {
		List<Gender> genders = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM gender";
		try {
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Gender gender = new Gender();
				gender.setId(rs.getLong(1));
				gender.setName(rs.getString(2));
				genders.add(gender);
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
					DBConnection.closeConnection(connection);
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return genders;
	}
}

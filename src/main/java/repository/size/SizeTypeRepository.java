package repository.size;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import entity.SizeType;

public class SizeTypeRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public List<SizeType> getAllSizeType() {
		List<SizeType> sizeTypes = new ArrayList<>();
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM size_type";
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				SizeType sizeType = new SizeType(rs.getLong(1), rs.getString(2));
				sizeTypes.add(sizeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		return sizeTypes;
	}
}

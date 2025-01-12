package repository.size;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import entity.Size;

public class SizeRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public List<Size> getAllSize() {
		connection = DBConnection.getConection();
		List<Size> sizes = new ArrayList<>();
		String sql = "SELECT * FROM size";
		try {
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Size size = new Size(rs.getLong(1), rs.getString(2), null);
				sizes.add(size);
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
		return sizes;
	}

	public Size findByName(String name) {
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM size where name = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return new Size(rs.getLong(1), rs.getString(2), null);

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
		return null;
	}

	public List<Size> getSizeBySizeType(long sizeTypeId) {
		connection = DBConnection.getConection();
		List<Size> sizes = new ArrayList<>();
		String sql = "SELECT * FROM size WHERE size_type_id = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setLong(1, sizeTypeId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Size size = new Size(rs.getLong(1), rs.getString(2), null);
				sizes.add(size);
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
		return sizes;
	}

}

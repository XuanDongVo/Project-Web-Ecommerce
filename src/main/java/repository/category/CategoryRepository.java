package repository.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dbConnection.DBConnection;
import entity.Category;
import entity.Gender;

public class CategoryRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public List<String> beadCrumb(String category) {
		List<String> beadCrumbs = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT g.name gender ,c.name category FROM category c\r\n"
				+ "INNER JOIN gender g ON g.id=c.gender_id WHERE c.name = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, category);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				beadCrumbs.add(rs.getString("gender"));
				beadCrumbs.add(rs.getString("category"));
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
		return beadCrumbs;
	}

	// Get all category
	public List<Category> getAllCategoriesByGender(String gender) {
		List<Category> categories = new ArrayList<>();
		connection = DBConnection.getConection();
		String sql = "SELECT category.* , gender.name  FROM category "
				+ "INNER JOIN gender ON gender.id = category.gender_id WHERE gender.name = ? ";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, gender);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getLong(1));
				category.setName(rs.getString(2));

				Gender currentGender = new Gender();
				currentGender.setId(rs.getLong(4));
				currentGender.setName(rs.getString(5));
				category.setGender(currentGender);
				categories.add(category);
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
		return categories;
	}

	// Find Category By Name
	public Optional<Category> findByName(String categoryName) {
		connection = DBConnection.getConection();
		String sql = "SELECT category.* , gender.name  FROM ecommerce.category "
				+ "INNER JOIN gender ON gender.id = category.gender_id " + "WHERE category.name = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, categoryName);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Category category = new Category();
				category.setId(rs.getLong(1));
				category.setName(rs.getString("name"));
				Gender gender = new Gender();
				gender.setId(rs.getLong(4));
				gender.setName(rs.getString(5));
				category.setGender(gender);
				return Optional.of(category);
			}
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
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

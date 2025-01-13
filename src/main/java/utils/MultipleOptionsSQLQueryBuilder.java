package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import dto.request.MultipleOptionsProductRequest;
import entity.Product;

public class MultipleOptionsSQLQueryBuilder {

	public static int totalProduct = 0;

	// Hàm chính để tìm sản phẩm theo các bộ lọc và phân trang
	public static List<Product> findByMultipleOptions(MultipleOptionsProductRequest options, int currentPage) {
		List<Product> products = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();
		Connection connection = DBConnection.getConection();

		// Lấy tổng số sản phẩm
		totalProduct = getTotalProducts(options, connection);

		// Xây dựng câu truy vấn SQL để lấy danh sách sản phẩm
		String sql = buildSQLQuery(options, parameters, currentPage);

		try {
			PreparedStatement	pst = connection.prepareStatement(sql);
			setQueryParameters(pst, parameters); // Thiết lập tham số
			products = executeQuery(pst); // Thực thi truy vấn và lấy kết quả
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					DBConnection.closeConnection(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return products;
	}

	// Hàm lấy tổng số sản phẩm thỏa mãn các điều kiện
	private static int getTotalProducts(MultipleOptionsProductRequest options, Connection connection) {
		int total = 0;
		List<Object> parameters = new ArrayList<>();

		// Câu truy vấn để lấy tổng số sản phẩm
		String sql = "SELECT COUNT(DISTINCT p.name) AS total FROM product AS p "
				+ "INNER JOIN sub_category AS sc ON p.sub_category_id = sc.id "
				+ "INNER JOIN category AS c ON sc.category_id = c.id " + "INNER JOIN gender AS g ON c.gender_id = g.id "
				+ "INNER JOIN product_color_img AS pci ON p.id = pci.product_id "
				+ "INNER JOIN color AS col ON pci.color_id = col.id "
				+ "INNER JOIN product_sku AS ps ON pci.id = ps.product_color_img_id "
				+ "INNER JOIN size AS s ON ps.size_id = s.id " + "WHERE 1=1 ";

		// Áp dụng các điều kiện lọc
		sql = searchProduct(sql, options.getSearch(), parameters);
		sql = addColorFilter(sql, options.getColors(), parameters);
		sql = addSizeFilter(sql, options.getSizes(), parameters);
		sql = addGenderFilter(sql, options.getGender(), parameters);
		sql = addSubCategoryFilter(sql, options.getSubCategory(), parameters);
		sql = addCategoryFilter(sql, options.getCategory(), parameters);

		try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        setQueryParameters(pst, parameters);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                total = rs.getInt("total");
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

		return total;
	}

	// Hàm xây dựng câu truy vấn SQL từ các tùy chọn và phân trang
	private static String buildSQLQuery(MultipleOptionsProductRequest options, List<Object> parameters,
			int currentPage) {
		String sql = "SELECT DISTINCT p.* FROM product AS p " + "INNER JOIN sub_category AS sc ON p.sub_category_id = sc.id "
				+ "INNER JOIN category AS c ON sc.category_id = c.id " + "INNER JOIN gender AS g ON c.gender_id = g.id "
				+ "INNER JOIN product_color_img AS pci ON p.id = pci.product_id "
				+ "INNER JOIN color AS col ON pci.color_id = col.id "
				+ "INNER JOIN product_sku AS ps ON pci.id = ps.product_color_img_id "
				+ "INNER JOIN size AS s ON ps.size_id = s.id " + "WHERE 1=1 ";
		// Áp dụng từng điều kiện lọc
		sql = searchProduct(sql, options.getSearch(), parameters);
		sql = addColorFilter(sql, options.getColors(), parameters);
		sql = addSizeFilter(sql, options.getSizes(), parameters);
		sql = addGenderFilter(sql, options.getGender(), parameters);
		sql = addSubCategoryFilter(sql, options.getSubCategory(), parameters);
		sql = addCategoryFilter(sql, options.getCategory(), parameters);

		// Tính toán OFFSET và LIMIT cho phân trang
		int offset = (currentPage) * 12;
		sql += " LIMIT 12 OFFSET ?";

		// Thêm tham số OFFSET vào danh sách tham số
		parameters.add(offset);

		return sql;
	}

	// Hàm thêm điều kiện lọc theo màu sắc
	private static String addColorFilter(String sql, List<String> colors, List<Object> parameters) {
		if (colors != null && !colors.isEmpty()) {
			sql += " AND col.name IN (";
			for (int i = 0; i < colors.size(); i++) {
				sql += "?";
				parameters.add(colors.get(i));
				if (i < colors.size() - 1) {
					sql += ", ";
				}
			}
			sql += ")";
		}
		return sql;
	}

	// Hàm thêm điều kiện lọc theo kích cỡ
	private static String addSizeFilter(String sql, List<String> sizes, List<Object> parameters) {
		if (sizes != null && !sizes.isEmpty()) {
			sql += " AND s.name IN (";
			for (int i = 0; i < sizes.size(); i++) {
				sql += "?";
				parameters.add(sizes.get(i));
				if (i < sizes.size() - 1) {
					sql += ", ";
				}
			}
			sql += ")";
		}
		return sql;
	}

	// Hàm thêm điều kiện lọc theo giới tính
	private static String addGenderFilter(String sql, String gender, List<Object> parameters) {
		if (gender != null && !gender.isEmpty()) {
			sql += " AND g.name = ?";
			parameters.add(gender);
		}
		return sql;
	}

	// Hàm thêm điều kiện lọc theo danh mục phụ
	private static String addSubCategoryFilter(String sql, String subCategory, List<Object> parameters) {
		if (subCategory != null && !subCategory.isEmpty()) {
			sql += " AND sc.name = ?";
			parameters.add(subCategory);
		}
		return sql;
	}

	// Hàm thêm điều kiện lọc theo danh mục
	private static String addCategoryFilter(String sql, String category, List<Object> parameters) {
		if (category != null && !category.isEmpty()) {
			sql += " AND c.name = ?";
			parameters.add(category);
		}
		return sql;
	}

	// Hàm thêm điều kiện tìm kiếm theo tên sản phẩm
	private static String searchProduct(String sql, String search, List<Object> parameters) {
		if (search != null && !search.isEmpty()) {
			sql += " AND p.name LIKE ?";
			parameters.add(appendPercentage(search));
		}
		return sql;
	}

	// Hàm thêm dấu '%' vào từ tìm kiếm để hỗ trợ tìm kiếm theo kiểu LIKE
	private static String appendPercentage(String search) {
		String[] words = search.split("\\s+");
		StringBuilder result = new StringBuilder("%");
		for (String word : words) {
			result.append(word).append("% ");
		}
		return result.toString().trim();
	}

	// Hàm thiết lập các tham số cho PreparedStatement
	private static void setQueryParameters(PreparedStatement pst, List<Object> parameters) throws Exception {
		for (int i = 0; i < parameters.size(); i++) {
			pst.setObject(i + 1, parameters.get(i));
		}
	}

	// Hàm thực hiện truy vấn và trả về danh sách sản phẩm
	private static List<Product> executeQuery(PreparedStatement pst) throws Exception {
	    List<Product> products = new ArrayList<>();
	    try (ResultSet rs = pst.executeQuery()) {
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getLong("id"));
	            product.setName(rs.getString("name"));
	            product.setDescription(rs.getString("description"));
	            products.add(product);
	        }
	    }
	    return products;
	}


}

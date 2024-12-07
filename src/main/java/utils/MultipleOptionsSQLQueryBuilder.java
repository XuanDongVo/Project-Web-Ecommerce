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
	// Hàm chính để tìm sản phẩm theo các bộ lọc
    public static List<Product> findByMultipleOptions(MultipleOptionsProductRequest options) {
        List<Product> products = new ArrayList<>();
        List<Object> parameters = new ArrayList<>(); // Danh sách các tham số cho PreparedStatement

        // Xây dựng câu truy vấn SQL
        String sql = buildSQLQuery(options, parameters);

        // Thực hiện truy vấn và trả về kết quả
        try (Connection connection = DBConnection.getConection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            setQueryParameters(pst, parameters); // Thiết lập các tham số cho câu truy vấn
            products = executeQuery(pst); // Thực thi câu truy vấn và lấy kết quả
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

	// Hàm xây dựng câu truy vấn SQL từ các tùy chọn
	private static String buildSQLQuery(MultipleOptionsProductRequest options, List<Object> parameters) {
		String sql = "SELECT p.* FROM product AS p " + "INNER JOIN sub_category AS sc ON p.sub_category_id = sc.id "
				+ "INNER JOIN category AS c ON sc.category_id = c.id " + "INNER JOIN gender AS g ON c.gender_id = g.id "
				+ "INNER JOIN product_color_img AS pci ON p.id = pci.product_id "
				+ "INNER JOIN color AS col ON pci.color_id = col.id "
				+ "INNER JOIN product_sku AS ps ON pci.id = ps.product_color_img_id "
				+ "INNER JOIN size AS s ON ps.size_id = s.id " + "WHERE 1=1 "; // Dùng WHERE 1=1 để thêm điều kiện dễ
																				// dàng

		// Áp dụng từng điều kiện lọc
		sql = searchProduct(sql, options.getSearch(), parameters);
		sql = addColorFilter(sql, options.getColors(), parameters);
		sql = addSizeFilter(sql, options.getSizes(), parameters);
		sql = addGenderFilter(sql, options.getGender(), parameters);
		sql = addSubCategoryFilter(sql, options.getSubCategory(), parameters);
		sql = addCategoryFilter(sql, options.getCategory(), parameters);

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

	private static String searchProduct(String sql, String search, List<Object> parameters) {
		if (search != null && !search.isEmpty()) {
			sql += " AND p.name like ?";
			parameters.add(appendPercentage(search));
		}
		return sql;
	}

	private static String  appendPercentage(String search) {
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
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setName(rs.getString("name"));
			product.setDescription(rs.getString("description"));
			products.add(product);
		}

		return products;
	}

	public static void main(String[] args) {
		MultipleOptionsProductRequest request = new MultipleOptionsProductRequest();
		request.setSearch("ao thun");
		findByMultipleOptions(request);
	}

}

package controller.admin.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dto.request.AddProductInDatabaseRequest;
import entity.Color;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.category.SubCategoryService;
import service.color.ColorService;
import service.product.ProductService;
import service.size.SizeService;

@WebServlet("/adminAddProduct")
public class AdminAddProductController extends HttpServlet {
	private ColorService colorService = new ColorService();
	private SizeService sizeService = new SizeService();
	private SubCategoryService subCategoryService = new SubCategoryService();
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("view".equalsIgnoreCase(action)) {
			viewAddAdminProduct(req, resp);
			return;
		} else {
			resp.sendRedirect(req.getContextPath() + "/adminAddProduct?action=view");
		}

	}

	private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String product = req.getParameter("product");
		// Chuyển đổi JSON thành đối tượng Java
		Gson gson = new Gson();
		AddProductInDatabaseRequest databaseRequest = gson.fromJson(product, AddProductInDatabaseRequest.class);

		// Thêm sản phẩm vào database
		String message;
		try {
			productService.addProduct(databaseRequest );
			message = "Thêm sản phẩm thành công";
		} catch (Exception e) {
			message = "Không thể thêm sản phẩm: " + e.getMessage();
		}

		try {
			String encodedMessage = URLEncoder.encode(message, "UTF-8");
			resp.sendRedirect(
					req.getContextPath() + "/adminAddProduct?action=view&message=" + encodedMessage);
		} catch (UnsupportedEncodingException e) {
			// Xử lý nếu gặp lỗi mã hóa
			e.printStackTrace();
		}
	}

	private void viewAddAdminProduct(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String message = req.getParameter("message");
		List<Color> colors = colorService.getAllColor();
		Map<String, List<String>> sizeMap = sizeService.getSizeBySizeType();
		Map<String, List<String>> genderToSubCategoryMap = subCategoryService.getSubCategoryByGender();

		req.setAttribute("colors", colors);
		req.setAttribute("sizeMap", sizeMap);
		req.setAttribute("genderToSubCategoryMap", genderToSubCategoryMap);
		if (message != null) {
			req.setAttribute("message", message);
		}

		req.getRequestDispatcher( "view/admin/admin_add_product.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addProduct(req, resp);
	}

}

package controller.admin.product;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.product.ProductService;

@WebServlet("/adminUpdateProductColorImg")
public class AdminUpdateProductColorImgController extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long productColorImgId = Long.parseLong(req.getParameter("colorImgId"));
		String color = req.getParameter("color");
		String imageBase64 = req.getParameter("image-" + color);

		String message;

		try {
			productService.updateProductColorImg(productColorImgId, imageBase64, color);
			message = "Cập Nhật sản phẩm thành công";
		} catch (SQLException e) {
			message = "Không thể cập nhật sản phẩm ";
		} 

		String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

		// đường dẫn trước đó
		String referer = req.getHeader("Referer");
		if (referer != null) {
			// Kiểm tra nếu URL chứa tham số "message"
			if (referer.contains("&message=")) {
				// Loại bỏ tham số message
				referer = referer.replaceAll("[&]message=[^&]*", "");
				// Đảm bảo rằng URL không kết thúc bằng dấu `?` hoặc `&`
				referer = referer.replaceAll("[?&]$", "");
			}
		}

		resp.sendRedirect(referer + "&message=" + encodedMessage);
	}
}

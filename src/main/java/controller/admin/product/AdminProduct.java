package controller.admin.product;

import java.io.IOException;
import java.util.List;

import dto.response.ProductDetailResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.product.ProductService;

@WebServlet("/adminProduct")
public class AdminProduct extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ProductDetailResponse> detailResponses = productService.getAllProduct();
		req.setAttribute("products", detailResponses);
		req.getRequestDispatcher("/view/admin/admin_product.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

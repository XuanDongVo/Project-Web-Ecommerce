package controller.user.productDetail;

import java.io.IOException;

import dto.response.ProductDetailResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.product.ProductService;

@WebServlet("/productDetail")
public class ProductDetailForward extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long productId = Long.parseLong(req.getParameter("id"));
		
		ProductDetailResponse productResponse = productService.getSkusById(productId);
		req.setAttribute("productResponse", productResponse);
		req.getRequestDispatcher("view/user/product_detail.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

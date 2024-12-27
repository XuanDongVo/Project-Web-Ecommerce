package controller.user.home;

import java.io.IOException;
import java.util.List;

import dto.respository.ProductDetailResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.product.ProductService;

@WebServlet("/home")
public class HomeController extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ProductDetailResponse> listResponses = productService.getRandomProductSku(2);
		req.setAttribute("listResponses", listResponses);
		req.getRequestDispatcher("view/user/home.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

package controller.admin.product;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.product.ProductService;

@WebServlet("/adminDeleteProduct")
public class AdminDeleteProductController extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long productId = Long.parseLong(req.getParameter("productId"));
		try {
			productService.deleteProduct(productId, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(req.getContextPath()+"/adminProduct");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

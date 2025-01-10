package controller.admin.main_admin;

import java.io.IOException;
import java.util.List;

import dto.response.AdminOrderResponse;
import dto.response.AdminUserResponse;
import dto.response.ProductDetailResponse;
import entity.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.order.OrderRepository;
import repository.user.UserRepository;
import service.product.ProductService;
@WebServlet("/adminController")
public class AdminController extends HttpServlet{
	UserRepository userRespository = new UserRepository();
	OrderRepository orderRepo =new OrderRepository();
	ProductService productService = new ProductService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<AdminUserResponse> list_customer = userRespository.getAllCustomer();
		List<AdminOrderResponse> list_order = orderRepo.getAllOrderWithResponse();
		List<ProductDetailResponse> detailResponses = productService.getAllProduct();
		req.setAttribute("products", detailResponses);
		req.setAttribute("list_order", list_order);
		req.setAttribute("list_customer", list_customer);
		req.getRequestDispatcher("view/admin/admin.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

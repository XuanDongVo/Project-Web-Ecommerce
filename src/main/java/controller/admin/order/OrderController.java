package controller.admin.order;

import java.io.IOException;
import java.util.List;

import dto.response.AdminOrderResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.order.OrderRepository;
@WebServlet("/order")
public class OrderController extends HttpServlet{
	OrderRepository orderRepo = new OrderRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<AdminOrderResponse> list = orderRepo.getAllOrderWithResponse();
		req.setAttribute("list_order", list);
		req.getRequestDispatcher("view/admin/admin_order.jsp").forward(req, resp);
//		resp.sendRedirect(req.getContextPath() + "/view/admin/admin_order.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
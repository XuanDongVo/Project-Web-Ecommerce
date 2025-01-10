package controller.admin.order;

import java.io.IOException;

import dto.response.AdminOrderResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.order.OrderRepository;
@WebServlet("/update_order")
public class UpdateOrderView extends HttpServlet{
	OrderRepository orderReposritory = new OrderRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String orderId = req.getParameter("order_id");

	    if (orderId != null && !orderId.isEmpty()) {
	        try {
	            Long idConvert = Long.parseLong(orderId);
	            AdminOrderResponse aor = orderReposritory.getOrderById(idConvert);
	            
	            req.setAttribute("order", aor);
	            req.getRequestDispatcher("/view/admin/admin_order_update.jsp").forward(req, resp);
	        } catch (NumberFormatException e) {
	            req.setAttribute("error", "ID đơn hàng không hợp lệ.");
	            resp.sendRedirect(req.getContextPath() + "/order");
	        }
	    } else {
	        req.setAttribute("error", "Không có ID đơn hàng để xóa.");
	        resp.sendRedirect(req.getContextPath() + "/order");
	    }
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
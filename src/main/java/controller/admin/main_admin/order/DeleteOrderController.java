package controller.admin.order;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.order.OrderRepository;
@WebServlet("/delete_order")
public class DeleteOrderController extends HttpServlet {
	OrderRepository orderRepo = new OrderRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String orderId = req.getParameter("order_id");

	    if (orderId != null && !orderId.isEmpty()) {
	        try {
	            Long idConvert = Long.parseLong(orderId);

	            // Xóa đơn hàng
	            boolean isDeleted = orderRepo.deleteOrder(idConvert);

	            if (isDeleted) {
	                // Chuyển hướng về trang danh sách đơn hàng
	                resp.sendRedirect(req.getContextPath() + "/order");
	            } else {
	                req.setAttribute("error", "Không thể xóa đơn hàng. Vui lòng thử lại.");
	                resp.sendRedirect(req.getContextPath() + "/order");
	            }
	        } catch (NumberFormatException e) {
	            req.setAttribute("error", "ID đơn hàng không hợp lệ.");
	            resp.sendRedirect(req.getContextPath() + "/order");
	        }
	    } else {
	        req.setAttribute("error", "Không có ID đơn hàng để xóa.");
	        resp.sendRedirect(req.getContextPath() + "/order");
	    }
	}

}
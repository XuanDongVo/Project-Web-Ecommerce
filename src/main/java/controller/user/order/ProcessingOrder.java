package controller.user.order;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;

import dto.request.OrderRequest;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.inventory.InventoryService;
import service.order.OrderService;

@WebServlet("/processingOrder")
public class ProcessingOrder extends HttpServlet {
	private OrderService orderService = new OrderService();
	private InventoryService inventoryService = new InventoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String customerName = req.getParameter("customerName");
		String customerEmail = req.getParameter("customerEmail");
		String customerPhone = req.getParameter("customerPhone");
		String address = req.getParameter("address");
		double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));

		String encodedCartIds = (String) getServletContext().getAttribute("selectedCartIds");
		// Giải mã URL
		String decodedCartIds = URLDecoder.decode(encodedCartIds, "UTF-8");

		// Chuyển đổi JSON thành mảng String[] bằng Gson
		String[] selectedCartIds = new Gson().fromJson(decodedCartIds, String[].class);

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		OrderRequest orderRequest = new OrderRequest(totalPrice, customerName, customerEmail, customerPhone, address,
				selectedCartIds);

		// kiểm tra hàng tồn kho trước khi cho phép tạo order
		Map<String, Integer> outOfStockProducts = inventoryService.checkStock(selectedCartIds, user, req);
		if (!outOfStockProducts.isEmpty()) {
			getServletContext().setAttribute("outOfStockProducts", outOfStockProducts);
			String safeEncodedCartIds = URLEncoder.encode(decodedCartIds, StandardCharsets.UTF_8.toString());
			resp.sendRedirect("checkout?selectedCartIds=" + safeEncodedCartIds);
			return;

		}
		orderService.processOrderItems(user, orderRequest, req, resp);
		getServletContext().removeAttribute("selectedCartIds");
		getServletContext().removeAttribute("outOfStockProducts");
		req.getRequestDispatcher("view/user/successOrder.jsp").forward(req, resp);

	}
}

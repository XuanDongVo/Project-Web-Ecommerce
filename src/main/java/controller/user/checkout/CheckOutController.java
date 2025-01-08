package controller.user.checkout;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dto.response.DetailCartResponse;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.cartdetail.CartDetailService;

@WebServlet("/checkout")
public class CheckOutController extends HttpServlet {
	private CartDetailService cartDetailService = new CartDetailService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Lấy và giải mã selectedCartIds
		String encodedCartIds = req.getParameter("selectedCartIds");
		// Giải mã URL
		String decodedCartIds = URLDecoder.decode(encodedCartIds, "UTF-8");

		// Chuyển đổi JSON thành mảng String[] bằng Gson
		String[] selectedCartIds = new Gson().fromJson(decodedCartIds, String[].class);

		List<DetailCartResponse> selectedProducts = cartDetailService.getSelectProductsForCheckout(user,
				selectedCartIds, req);

		getServletContext().setAttribute("selectedCartIds", encodedCartIds);
		@SuppressWarnings("unchecked")
		Map<String, Integer> outOfStockProducts = (Map<String, Integer>) getServletContext()
				.getAttribute("outOfStockProducts");
		if (outOfStockProducts != null) {
			req.setAttribute("outOfStockProducts", outOfStockProducts);
			getServletContext().removeAttribute("outOfStockProducts");
		}
		req.setAttribute("selectedProducts", selectedProducts);
		req.getRequestDispatcher("view/user/checkout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

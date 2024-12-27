package controller.user.navbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dto.respository.DetailCartResponse;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.cartdetail.CartDetailService;
import service.category.SubCategoryService;

@WebServlet("/header")
public class NavbarController extends HttpServlet {
	private CartDetailService cartDetailService = new CartDetailService();
	private SubCategoryService subCategoryService = new SubCategoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		Integer quantityProduct = cartDetailService.getQuantityProductFromCart(user, req);
		List<DetailCartResponse> cartResponse = cartDetailService.removeNonExistentProducts(user, req , resp);
		
		Map<String, Map<String, List<String>>> dropListCategory = subCategoryService.dropListCategory();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("dropListCategory", dropListCategory);
		responseData.put("quantityProduct", quantityProduct);
		responseData.put("listCartDetail", cartResponse);

		// Thiáº¿t láº­p kiá»ƒu dá»¯ liá»‡u tráº£ vá»� lĂ  JSON
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		// Chuyá»ƒn Ä‘á»‘i tÆ°á»£ng thĂ nh JSON vĂ  ghi vĂ o pháº£n há»“i
		String jsonResponse = gson.toJson(responseData);
		resp.getWriter().write(jsonResponse);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

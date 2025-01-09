package controller.user.product_cate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import dto.request.MultipleOptionsProductRequest;
import dto.response.PaginationResponse;
import dto.response.ProductDetailResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.color.ColorService;
import service.product.ProductService;

@WebServlet("/gender")
public class ProductByGenderController extends HttpServlet {
	private ProductService productService = new ProductService();
	private ColorService colorService = new ColorService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gender = req.getParameter("gender");
		String sizeParam = req.getParameter("size");
		String colorParam = req.getParameter("color");
		String currentPageParam = req.getParameter("page");
		int currentPage = (currentPageParam == null) ? 0 : Integer.parseInt(currentPageParam)-1;

		// Chuyển đổi tham số thành danh sách nếu không rỗng
		List<String> sizes = (sizeParam != null && !sizeParam.isEmpty()) ? Arrays.asList(sizeParam.split(",")) : null;
		List<String> colors = (colorParam != null && !colorParam.isEmpty()) ? Arrays.asList(colorParam.split(","))
				: null;

		// Kiểm tra xem đây có phải là yêu cầu AJAX hay không
		boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

		if (sizes == null && colors == null) {
			// Không có bộ lọc, trả về JSP
			PaginationResponse listResponses = productService.getSkusByGender(gender, null, currentPage);
			req.setAttribute("listResponses", listResponses);

			// Thiết lập breadcrumb
			List<String> beadcrumb = new ArrayList<>();
			beadcrumb.add(gender);
			req.setAttribute("beadcrumb", beadcrumb);

			if (!isAjax) {
				// Chuyển tiếp đến JSP cho các yêu cầu thông thường
				req.getRequestDispatcher("view/user/product_cate.jsp").forward(req, resp);
			} else {
				// Nếu là yêu cầu AJAX, trả về JSON
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				Gson gson = new Gson();
				String jsonResponse = gson.toJson(listResponses);
				resp.getWriter().write(jsonResponse);
			}
		} else {
			// Có bộ lọc, trả về phản hồi JSON
			MultipleOptionsProductRequest mutileOption = new MultipleOptionsProductRequest();
			mutileOption.setGender(gender);
			mutileOption.setColors(colors);
			mutileOption.setSizes(sizes);

			PaginationResponse listResponses = productService.getSkusByGender(gender, mutileOption, currentPage);

			// Thiết lập phản hồi JSON
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String jsonResponse = gson.toJson(listResponses);
			resp.getWriter().write(jsonResponse);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

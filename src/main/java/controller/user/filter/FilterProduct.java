package controller.user.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import entity.Color;
import entity.Size;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.color.ColorService;
import service.size.SizeService;

@WebServlet("/filter")
public class FilterProduct extends HttpServlet {
	private ColorService colorService = new ColorService();
	private SizeService sizeService = new SizeService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Color> colors = colorService.getAllColor();
		List<Size> sizes = sizeService.getAllSize();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("colors", colors);
		responseData.put("sizes", sizes);
//		responseData.put("listCartDetail", cartResponse);

		// Thiết lập kiểu dữ liệu trả về là JSON
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		// Chuyển đối tượng thành JSON và ghi vào phản hồi
		String jsonResponse = gson.toJson(responseData);
		resp.getWriter().write(jsonResponse);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

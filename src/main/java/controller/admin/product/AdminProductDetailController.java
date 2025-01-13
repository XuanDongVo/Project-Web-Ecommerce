package controller.admin.product;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import dto.response.ProductDetailResponse;
import entity.Category;
import entity.Color;
import entity.SubCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.category.CategoryRepository;
import repository.category.SubCategoryRepository;
import service.color.ColorService;
import service.product.ProductService;

@WebServlet("/adminDetailProduct")
public class AdminProductDetailController extends HttpServlet {
	private ProductService productService = new ProductService();
	private SubCategoryRepository subCategoryRepository = new SubCategoryRepository();
	private CategoryRepository categoryRepository = new CategoryRepository();
	private ColorService colorService = new ColorService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		Long id = Long.parseLong(req.getParameter("id"));

		ProductDetailResponse detailResponse = productService.getSkusById(id);
		req.setAttribute("productResponse", detailResponse);
		if (action.equalsIgnoreCase("view")) {

			req.getRequestDispatcher("/view/admin/admin_product_detail.jsp").forward(req, resp);
			return;
		} else if (action.equalsIgnoreCase("edit")) {
			String message = req.getParameter("message");
			String decodeMessage = null;
			if (message != null) {
				decodeMessage = URLDecoder.decode(message, StandardCharsets.UTF_8.toString());
			}
			Category category = categoryRepository.findBySubCategoryName(detailResponse.getSubCategory());
			List<SubCategory> subCategories = subCategoryRepository.getSubCategoryByCategory(category.getName());
			List<Color> colors = colorService.getAllColor();
			req.setAttribute("colors", colors);
			req.setAttribute("subCategory", subCategories);
			req.setAttribute("message", decodeMessage);
			req.getRequestDispatcher("/view/admin/admin_update_product.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

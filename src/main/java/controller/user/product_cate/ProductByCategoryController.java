package controller.user.product_cate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import dto.request.MultipleOptionsProductRequest;
import dto.response.PaginationResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.category.CategoryService;
import service.product.ProductService;

@WebServlet("/category")
public class ProductByCategoryController extends HttpServlet {
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("category");
        String sizeParam = req.getParameter("size");
        String colorParam = req.getParameter("color");
        
        String currentPageParam = req.getParameter("page");
		int currentPage = (currentPageParam == null) ? 0 : Integer.parseInt(currentPageParam)-1;

        // Chuyển các tham số thành mảng nếu chúng không rỗng, bỏ qua nếu null hoặc rỗng
        List<String> sizes = (sizeParam != null && !sizeParam.isEmpty()) ? Arrays.asList(sizeParam.split(",")) : null;
        List<String> colors = (colorParam != null && !colorParam.isEmpty()) ? Arrays.asList(colorParam.split(",")) : null;

        // Kiểm tra xem có bộ lọc hay không
        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (sizes == null && colors == null) {
            // Không có bộ lọc, trả về JSP
        	PaginationResponse listResponses = productService.getSkusByCategory(category, null ,currentPage );
            req.setAttribute("listResponses", listResponses);

            // Thiết lập breadcrumb
            List<String> beadcrumb = categoryService.beadCrumb(category);
            req.setAttribute("beadcrumb", beadcrumb);

            // Chỉ chuyển tiếp đến JSP nếu không phải là yêu cầu AJAX
            if (!isAjax) {
                req.getRequestDispatcher("view/user/product_cate.jsp").forward(req, resp);
            } else {
                // Nếu là yêu cầu AJAX, trả về JSON cho các yêu cầu không có bộ lọc
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(listResponses);
                resp.getWriter().write(jsonResponse);
            }
        } else {
            // Có bộ lọc, trả về JSON
            MultipleOptionsProductRequest mutileOption = new MultipleOptionsProductRequest();
            mutileOption.setCategory(category);
            mutileOption.setColors(colors);
            mutileOption.setSizes(sizes);

            PaginationResponse listResponses = productService.getSkusByCategory(category, mutileOption, currentPage);

            // Thiết lập kiểu dữ liệu trả về là JSON
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            // Chuyển đối tượng thành JSON và ghi vào phản hồi
            String jsonResponse = gson.toJson(listResponses);
            resp.getWriter().write(jsonResponse);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

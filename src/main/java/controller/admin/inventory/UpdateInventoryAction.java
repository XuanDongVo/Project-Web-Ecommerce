package controller.admin.inventory;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.inventory.InventoryRepository;
@WebServlet("/update_inventory")
public class UpdateInventoryAction extends HttpServlet{
	InventoryRepository inventoryRepo = new InventoryRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inventoryId = req.getParameter("inventoryId");
		String stock = req.getParameter("stock");
		try {
			long inventoryId_convert = Long.parseLong(inventoryId);
			int stock_convert = Integer.parseInt(stock);
			if(inventoryRepo.updateInventoryByInventoryId(inventoryId_convert, stock_convert)){
				resp.sendRedirect(req.getContextPath()+"/inventory");
			}else {
				req.setAttribute("error", "Sửa sản phẩm này bị lỗi");
				req.getRequestDispatcher("view/admin/admin_update_inventory.jsp").forward(req, resp);
			}
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
			req.setAttribute("error", "Stock nhập vào không phải là số");
			req.getRequestDispatcher("view/admin/admin_update_inventory.jsp").forward(req, resp);
		}
	}
}
package controller.admin.inventory;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.inventory.InventoryRepository;
@WebServlet("/delete_inventory")
public class DeleteInventoryAction extends HttpServlet{
	InventoryRepository inventoryRepo = new InventoryRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inventoryId = req.getParameter("inventory_id");
		try {
			long inventoryId_convert = Long.parseLong(inventoryId);
			if(inventoryRepo.deleteInventoryByInventoryId(inventoryId_convert)){
				resp.sendRedirect(req.getContextPath()+"/inventory");
			}else {
				req.setAttribute("error", "Sản phẩm này xóa thất bại");
				req.getRequestDispatcher(req.getContextPath()+"/inventory").forward(req, resp);
			}
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
			req.setAttribute("error", "Stock nhập vào không phải là số");
			req.getRequestDispatcher(req.getContextPath()+"/inventory").forward(req, resp);
		}
	}
}
package controller.admin.inventory;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.inventory.InventoryRepository;
@WebServlet("/add_inventory")
public class AddInventoryAction extends HttpServlet{
	InventoryRepository inventoryRepo = new InventoryRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String skuId = req.getParameter("sku_id");
		String stock = req.getParameter("stock");
		try {
			long skuId_convert = Long.parseLong(skuId);
			int stock_convert = Integer.parseInt(stock);
			if(!inventoryRepo.checkInventoryBySkuId(skuId_convert)) {
				if(inventoryRepo.addInventory(skuId_convert, stock_convert)) {
					resp.sendRedirect(req.getContextPath()+"/inventory");
				}else {
					req.setAttribute("error", "Thêm thất bại");
					req.getRequestDispatcher(req.getContextPath()+"/add_inventory_view").forward(req, resp);
				}
				
			}else {
				req.setAttribute("error", "Sản phẩm này đã thêm vào kho");
				req.getRequestDispatcher("/add_inventory_view").forward(req, resp);
			}
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
			req.setAttribute("error", "Stock nhập vào không phải là số");
			req.getRequestDispatcher("/add_inventory_view").forward(req, resp);
		}
		
	}
}
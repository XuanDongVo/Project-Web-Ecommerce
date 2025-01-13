package controller.admin.inventory;

import java.io.IOException;
import java.util.List;

import entity.ProductSku;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.inventory.InventoryRepository;
@WebServlet("/add_inventory_view")
public class AddInventoryView extends HttpServlet{
	InventoryRepository inventoryRepo = new InventoryRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ProductSku> list_productInventory = inventoryRepo.getAllProductSku();
		req.setAttribute("list_productInventory", list_productInventory);
		req.getRequestDispatcher("view/admin/admin_add_inventory.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
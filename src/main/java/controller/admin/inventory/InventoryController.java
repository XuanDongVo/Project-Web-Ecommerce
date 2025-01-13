package controller.admin.inventory;

import java.io.IOException;
import java.util.List;

import dto.response.AdminInventory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.inventory.InventoryRepository;

@WebServlet("/inventory")
public class InventoryController extends HttpServlet {
	InventoryRepository inventoryRepo = new InventoryRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<AdminInventory> list_inventory = inventoryRepo.getAllInventory();
		req.setAttribute("list_inventory", list_inventory);
		req.getRequestDispatcher("view/admin/admin_inventory.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
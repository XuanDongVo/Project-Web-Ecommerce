package service.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.respository.DetailCartResponse;
import entity.Inventory;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import repository.inventory.InventoryRepository;
import service.cartdetail.CartDetailService;

public class InventoryService {
	private InventoryRepository inventoryRepository = new InventoryRepository();
	private CartDetailService cartDetailService = new CartDetailService();

	public Map<String, Integer> checkStock(String[] ids, User user, HttpServletRequest request) {
		Map<String, Integer> outOfStockProducts = new HashMap<>();
		List<DetailCartResponse> filteredDetailCarts;
		if (user != null) {
			filteredDetailCarts = null;
		} else {
			// Láº¥y giá»� hĂ ng tá»« cookie hiá»‡n táº¡i (náº¿u cĂ³)
			List<DetailCartResponse> detailCarts = new ArrayList<>();
	
			String cartCookieValue = cartDetailService.getCookieValue(request, "cart");

			// Náº¿u Ä‘Ă£ cĂ³ cookie giá»� hĂ ng, chuyá»ƒn Ä‘á»•i chuá»—i JSON tá»« cookie thĂ nh danh sĂ¡ch
			// CartDetail
			if (cartCookieValue != null && !cartCookieValue.isEmpty()) {
				detailCarts = cartDetailService.decodeCartDetailsFromCookie(cartCookieValue);
			}
			List<String> idsList = Arrays.asList(ids);

			filteredDetailCarts = detailCarts.stream()
					.filter(cart -> idsList.stream().anyMatch(id -> cart.getCartId().equals(Long.valueOf(id))))
					.toList();
		}

		for (DetailCartResponse item : filteredDetailCarts) {
			Inventory inventory = inventoryRepository.findByProductSkuId(item.getCartId()).get();
			int stock = inventory.getStock();
			if (item.getQuantity() > stock) {
				outOfStockProducts.put(inventory.getProductSku().getProductColorImage().getProduct().getName(),
						inventory.getStock());
			}
		}
		return outOfStockProducts;
	}
}

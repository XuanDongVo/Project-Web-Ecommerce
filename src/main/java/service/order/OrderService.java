package service.order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dbConnection.DBConnection;
import dto.request.OrderRequest;
import dto.respository.DetailCartResponse;
import entity.CartDetail;
import entity.Inventory;
import entity.OrderDetail;
import entity.ProductSku;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.cart.CartDetailRepository;
import repository.inventory.InventoryRepository;
import repository.order.OrderDetailRepository;
import repository.order.OrderRepository;
import repository.product.ProductSkuRepository;
import service.cartdetail.CartDetailService;

public class OrderService {
	private OrderRepository orderRepository = new OrderRepository();
	private CartDetailService cartDetailService = new CartDetailService();
	private ProductSkuRepository productSkuRepository = new ProductSkuRepository();
	private InventoryRepository inventoryRepository = new InventoryRepository();
	private OrderDetailRepository orderDetailRepository = new OrderDetailRepository();
	private CartDetailRepository cartDetailRepository = new CartDetailRepository();

	public void processOrderItems(User user, OrderRequest orderRequest, HttpServletRequest request,
			HttpServletResponse response) {
		Connection connection = null;
		try {
			// Táº¡o Ä‘Æ¡n hĂ ng
			connection = DBConnection.getConection();
			Long orderId = orderRepository.createOrder(connection, orderRequest, user);
			if (orderId == 0) {
				throw new RuntimeException("Cannot create order");
			}
			if (user == null) {
				createOrderDetailForAnonymous(orderId, orderRequest, request, response);
			} else {
				createOrderDetailInDatabase(orderId, orderRequest.getIds());
			}
			// HoĂ n táº¥t giao dá»‹ch
			orderRepository.finalizeTransaction(connection);
		} catch (Exception e) {
			e.printStackTrace();
			orderRepository.rollbackTransaction(connection);
			throw new RuntimeException("Error processing order items", e);
		}
	}

	// tao don dat hang cho nguoi dung da dang nhap
	private void createOrderDetailInDatabase(Long orderId, String[] cartDetailIds) {
		for (int i = 0; i < cartDetailIds.length; i++) {
			Long cartId = Long.parseLong(cartDetailIds[i]);
			CartDetail cartDetail = cartDetailRepository.findById(cartId).orElseThrow();

			ProductSku productSku = productSkuRepository.findById(cartDetail.getProductSku().getId());

			// tao order detail cho khach hang
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderId);

			orderDetail.setProductSkuId(productSku.getId());
			orderDetail.setPrice(productSku.getPrice());
			orderDetail.setQuantity(cartDetail.getQuantity());

			// cáº­p nháº­t hĂ ng tá»“n kho trong kho hĂ ng
			Inventory invetory = inventoryRepository.findByProductSkuId(productSku.getId()).get();
			invetory.setStock(invetory.getStock() - cartDetail.getQuantity());
			orderDetailRepository.save(orderDetail);
			inventoryRepository.updateQuantityByInvetoryid(invetory.getId(), invetory.getStock());

			// xoa san pham trong gio hang
			cartDetailRepository.deleteProductSkuInCartDetail(cartId);
		}
	}

	// tao don dat hang cho nguoi an danh
	private void createOrderDetailForAnonymous(Long orderId, OrderRequest orderRequest, HttpServletRequest request,
			HttpServletResponse response) {
		// Láº¥y giá»� hĂ ng tá»« cookie hiá»‡n táº¡i (náº¿u cĂ³)
		List<DetailCartResponse> detailCarts = new ArrayList<>();

		String cartCookieValue = cartDetailService.getCookieValue(request, "cart");

		// Náº¿u Ä‘Ă£ cĂ³ cookie giá»� hĂ ng, chuyá»ƒn Ä‘á»•i chuá»—i JSON tá»« cookie thĂ nh danh sĂ¡ch
		// CartDetail
		if (cartCookieValue != null && !cartCookieValue.isEmpty()) {
			detailCarts = cartDetailService.decodeCartDetailsFromCookie(cartCookieValue);
		}

		String[] ids = orderRequest.getIds();
		List<String> idsList = Arrays.asList(ids);

		List<DetailCartResponse> filteredDetailCarts = detailCarts.stream()
				.filter(cart -> idsList.stream().anyMatch(id -> cart.getCartId().equals(Long.valueOf(id)))).toList();

		// táº¡o order Detail cho khach hang
		filteredDetailCarts.forEach(cartDetail -> {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderId);

			ProductSku productSku = productSkuRepository.findById(cartDetail.getCartId());

			orderDetail.setProductSkuId(productSku.getId());
			orderDetail.setPrice(productSku.getPrice());
			orderDetail.setQuantity(cartDetail.getQuantity());

			// cáº­p nháº­t hĂ ng tá»“n kho trong kho hĂ ng
			Inventory invetory = inventoryRepository.findByProductSkuId(productSku.getId()).get();
			invetory.setStock(invetory.getStock() - cartDetail.getQuantity());
			orderDetailRepository.save(orderDetail);
			inventoryRepository.updateQuantityByInvetoryid(invetory.getId(), invetory.getStock());
		});

		detailCarts.removeAll(filteredDetailCarts);
		cartDetailService.updateCartCookie(response, detailCarts);

	}

}

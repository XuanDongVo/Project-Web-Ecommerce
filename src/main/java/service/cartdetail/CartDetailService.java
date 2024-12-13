package service.cartdetail;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dto.request.AddProductInCartRequest;
import dto.request.ModifyProductRequest;
import dto.respository.DetailCartResponse;
import entity.Cart;
import entity.CartDetail;
import entity.Product;
import entity.ProductColorImage;
import entity.ProductSku;
import entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.cart.CartDetailRepository;
import repository.cart.CartRepository;
import repository.product.ProductSkuRepository;

public class CartDetailService {
	private CartRepository cartRepository = new CartRepository();
	private CartDetailRepository cartDetailRepository = new CartDetailRepository();
	private ProductSkuRepository productSkuRepository = new ProductSkuRepository();

	// xĂ³a sáº£n pháº©m trong cookies náº¿u sáº£n pháº©m khĂ´ng cĂ²n trong cÆ¡ sá»Ÿ
	// dá»¯ liá»‡u
	public List<DetailCartResponse> removeNonExistentProducts(User user, HttpServletRequest request,
			HttpServletResponse servletResponse) {
		List<DetailCartResponse> list = getDetailsInCart(user, request);

		if (user != null) {
			return list;
		}

		for (DetailCartResponse detailCartResponse : list) {
			ProductSku productSku = productSkuRepository.findById(detailCartResponse.getCartId());
			// san pham da bi xoa trong database
			if (productSku == null) {
				removeProductForAnonymous(detailCartResponse.getCartId(), request, servletResponse);
			}
		}
		return getDetailsInCart(user, request);

	}

	// láº¥y ra nhá»¯ng sáº£n pháº©m mĂ  khĂ¡ch hĂ ng chá»�n Ä‘á»ƒ checkout
	public List<DetailCartResponse> getSelectProductsForCheckout(User user, String[] selectedCartIds,
			HttpServletRequest request) {
		if (user == null) {
			// láº¥y ra nhá»¯ng sáº£n pháº©m Ä‘Ă£ chá»�n trong cookie
			return getSelectProductsFromCookiesForCheckout(selectedCartIds, request);
		}
		return cartDetailRepository.getSelectProductsForCheckout(selectedCartIds);

	}

	// láº¥y ra sá»‘ lÆ°á»£ng sáº£n pháº©m trong giá»� hĂ ng
	public int getQuantityProductFromCart(User user, HttpServletRequest request) {
		// ngÆ°á»�i dĂ¹ng chÆ°a Ä‘Äƒng nháº­p
		if (user == null) {
			return getQuantityProductFromCartForAnonymous(request);
		}
		// ngÆ°á»�i dĂ¹ng Ä‘Ă£ dÄƒng nháº­p
		Cart cart = getUserCart(user);
		return cartDetailRepository.getQuantityProductFromCart(cart);

	}

	// láº¥y ra sá»‘ lÆ°á»£ng sáº£n pháº©m trong giá»� hĂ ng cá»§a ngÆ°á»�i dĂ¹ng
	// chÆ°a Ä‘Äƒng nháº­p
	private int getQuantityProductFromCartForAnonymous(HttpServletRequest request) {
		int quantity = 0;
		List<DetailCartResponse> detailCartResponses = getCartDetailsFromCookie(request);
		if (detailCartResponses == null) {
			return quantity;
		}
		for (DetailCartResponse detailCartResponse : detailCartResponses) {
			quantity += detailCartResponse.getQuantity();
		}
		return quantity;
	}

	// láº¥y danh sĂ¡ch sáº£n pháº©m trong giá»� hĂ ng cá»§a ngÆ°á»�i dĂ¹ng
	public List<DetailCartResponse> getDetailsInCart(User user, HttpServletRequest request) {
		// TĂ¬m giá»� hĂ ng cá»§a ngÆ°á»�i dĂ¹ng hiá»‡n táº¡i
		Cart cart = getUserCart(user);

		// Náº¿u giá»� hĂ ng khĂ´ng tá»“n táº¡i, tĂ¬m trong cookie
		if (cart == null) {
			return getCartDetailsFromCookie(request);
		}

		// Láº¥y chi tiáº¿t giá»� hĂ ng tá»« database
		return getCartDetailsFromDatabase(cart);
	}

	// cáº­p nháº­t giá»� hĂ ng táº¡m giá»� vĂ o giá»� hĂ ng chĂ­nh khi ngÆ°á»�i
	// dĂ¹ng Ä‘Äƒng nháº­p
	public void mergeCartAfterLogin(HttpServletRequest servletRequest, User user, HttpServletResponse servletResponse) {
		List<DetailCartResponse> cartResponses = getCartDetailsFromCookie(servletRequest);
		// kiá»ƒm tra cĂ³ sáº£n pháº©m trong cookies khĂ´ng
		if (cartResponses == null) {
			return;
		}
		// Láº¥y ra giá»� hĂ ng ngÆ°á»�i dĂ¹ng
		Cart cart = getUserCart(user);

		cartResponses.forEach((detail) -> {
			CartDetail cartDetail = cartDetailRepository.findByProductSkuAndCart(detail.getCartId(), cart.getId());
			// kiá»ƒm tra sáº£n pháº©m Ä‘Ă³ tá»“n táº¡i trong giá»� hĂ ng chÆ°a
			if (cartDetail == null) {
				cartDetailRepository.addProductSkuInCartDetail(detail.getCartId(), cart, detail.getQuantity());
			} else {
				// cáº­p nháº­t láº¡i sá»‘ lÆ°á»£ng trong giá»� hĂ ng
				cartDetailRepository.updateQuantityInCartDetail(cartDetail);
			}
		});
		Cookie[] cookies = servletRequest.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// Kiá»ƒm tra náº¿u cookie cĂ³ tĂªn lĂ  "cart"
				if ("cart".equals(cookie.getName())) {
					cookie.setMaxAge(0); // XĂ³a cookie ngay láº­p tá»©c
					cookie.setPath("/"); // Ä�áº£m báº£o cookie bá»‹ xĂ³a á»Ÿ táº¥t cáº£ cĂ¡c Ä‘Æ°á»�ng dáº«n
					servletResponse.addCookie(cookie); // Cáº­p nháº­t cookie trong response
				}
			}
		}
	}

	// thĂªm sáº£n pháº©m vĂ o trong giá»� hĂ ng
	public void addProductToCartDetail(AddProductInCartRequest productRequest, HttpServletResponse response,
			HttpServletRequest request, User user) {

		ProductSku productSku = productSkuRepository
				.findByProductColorImgAndSize(productRequest.getId(), productRequest.getSize())
				.orElseThrow(() -> new RuntimeException("not found"));

		// TĂ¬m giá»� hĂ ng cá»§a ngÆ°á»�i dĂ¹ng hiá»‡n táº¡i
		Cart cart = getUserCart(user);
		if (cart == null) {
			addCartDetailForAnonymous(productSku, productRequest, request, response);
			return;
		}

		CartDetail cartDetail = cartDetailRepository.findByProductSkuAndCart(productSku.getId(), cart.getId());
		// kiá»ƒm tra sáº£n pháº©m Ä‘Ă£ tá»“n táº¡i trong giá»� hĂ ng chÆ°a
		if (cartDetail == null) {
			// thĂªm sáº£n pháº©m vĂ o giá»� hĂ ng
			cartDetailRepository.addProductSkuInCartDetail(productSku.getId(), cart, productRequest.getQuantity());
			return;
		}
		// Cáº­p nháº­t sá»‘ lÆ°á»£ng sáº£n pháº©m trong CartDetail
		int updatedQuantity = cartDetail.getQuantity() + productRequest.getQuantity();
		cartDetail.setQuantity(updatedQuantity);

		cartDetailRepository.updateQuantityInCartDetail(cartDetail);
	}

	// chá»‰nh sá»­a sá»‘ lÆ°á»£ng sáº£n pháº©m trong giá»� hĂ ng
	public void modifyProductInCartDetail(ModifyProductRequest modifyProductRequest, HttpServletRequest request,
			HttpServletResponse response, User user) {
		Cart cart = getUserCart(user);
		// xá»­ lĂ­ ngÆ°á»�i dĂ¹ng áº©n danh
		if (cart == null) {
			modifyProductForAnonymous(modifyProductRequest, request, response);
			return;
		}
		CartDetail cartDetail = cartDetailRepository.findById(modifyProductRequest.getId())
				.orElseThrow(() -> new RuntimeException("Cart Detail not found by id " + modifyProductRequest.getId()));
		cartDetail.setQuantity(modifyProductRequest.getQuantity());
		cartDetailRepository.updateQuantityInCartDetail(cartDetail);
	}

	// xĂ³a sáº£n pháº©m trong giá»� hĂ ng
	public void removeProductInCartDetail(Long cartDetailId, User user, HttpServletRequest request,
			HttpServletResponse response) {
		Cart cart = getUserCart(user);
		// xá»­ lĂ­ ngÆ°á»�i dĂ¹ng áº©n danh
		if (cart == null) {
			removeProductForAnonymous(cartDetailId, request, response);
			return;
		}
		cartDetailRepository.deleteProductSkuInCartDetail(cartDetailId);
	}

	// chá»‰nh sá»­a sá»‘ lÆ°á»£ng sáº£n pháº©m trong giá»� hĂ ng cho ngÆ°á»�i áº©n
	// danh
	private void modifyProductForAnonymous(ModifyProductRequest modifyProductRequest, HttpServletRequest request,
			HttpServletResponse response) {
		List<DetailCartResponse> detailCarts;

		String cartCookieValue = getCookieValue(request, "cart");

		// Náº¿u Ä‘Ă£ cĂ³ cookie giá»� hĂ ng, chuyá»ƒn Ä‘á»•i chuá»—i JSON tá»« cookie
		// thĂ nh danh sĂ¡ch
		// CartDetail
		detailCarts = decodeCartDetailsFromCookie(cartCookieValue);

		// lá»�c láº¥y sáº£n pháº©m cáº§n modify
		Optional<DetailCartResponse> existingDetailCart = detailCarts.stream()
				.filter(cd -> cd.getCartId().equals(modifyProductRequest.getId())).findFirst();

		if (existingDetailCart.isPresent()) {
			// Náº¿u tĂ¬m tháº¥y sáº£n pháº©m, cáº­p nháº­t sá»‘ lÆ°á»£ng
			existingDetailCart.get().setQuantity(modifyProductRequest.getQuantity());
		} else {
			throw new RuntimeException("Product not found in cart for modification.");
		}

		// cáº­p nháº­t dá»¯ liá»‡u trong cookie
		updateCartCookie(response, detailCarts);
	}

	// xĂ³a sáº£n pháº©m trong giá»� hĂ ng táº¡m thá»�i
	public void removeProductForAnonymous(Long productSkuId, HttpServletRequest request, HttpServletResponse response) {
		List<DetailCartResponse> detailCarts;

		String cartCookieValue = getCookieValue(request, "cart");

		// Náº¿u Ä‘Ă£ cĂ³ cookie giá»� hĂ ng, chuyá»ƒn Ä‘á»•i chuá»—i JSON tá»« cookie
		// thĂ nh danh sĂ¡ch
		// CartDetail
		detailCarts = decodeCartDetailsFromCookie(cartCookieValue);

		// lá»�c láº¥y sáº£n pháº©m cáº§n modify
		Optional<DetailCartResponse> existingDetailCart = detailCarts.stream()
				.filter(cd -> cd.getCartId().equals(productSkuId)).findFirst();

		if (existingDetailCart.isPresent()) {
			// xĂ³a sáº£n pháº©m
			detailCarts.remove(existingDetailCart.get());
		}

		// cáº­p nháº­t dá»¯ liá»‡u trong cookie
		updateCartCookie(response, detailCarts);
	}

	// thĂªm sáº£n pháº©m vĂ o trong giá»� hĂ ng Ä‘á»‘i vá»›i ngÆ°á»�i áº©n danh
	private void addCartDetailForAnonymous(ProductSku productSku, AddProductInCartRequest productRequest,
			HttpServletRequest request, HttpServletResponse response) {
		// Láº¥y giá»� hĂ ng tá»« cookie hiá»‡n táº¡i (náº¿u cĂ³)
		List<DetailCartResponse> detailCarts = new ArrayList<>();

		String cartCookieValue = getCookieValue(request, "cart");

		// Náº¿u Ä‘Ă£ cĂ³ cookie giá»� hĂ ng, chuyá»ƒn Ä‘á»•i chuá»—i JSON tá»« cookie
		// thĂ nh danh sĂ¡ch
		// CartDetail
		if (cartCookieValue != null && !cartCookieValue.isEmpty()) {
			detailCarts = decodeCartDetailsFromCookie(cartCookieValue);
		}

		Optional<DetailCartResponse> existingCartDetail = detailCarts.stream()
				.filter(cd -> cd.getCartId().equals(productSku.getId())).findFirst();

		if (existingCartDetail.isPresent()) {
			// Náº¿u sáº£n pháº©m Ä‘Ă£ tá»“n táº¡i trong giá»� hĂ ng, cáº­p nháº­t sá»‘
			// lÆ°á»£ng
			DetailCartResponse detailCartResponse = existingCartDetail.get();
			int updatedQuantity = detailCartResponse.getQuantity() + productRequest.getQuantity();
			detailCartResponse.setQuantity(updatedQuantity);
		} else {
			ProductColorImage productColorImg = productSku.getProductColorImage();
			Product product = productColorImg.getProduct();

			// Náº¿u sáº£n pháº©m chÆ°a cĂ³ trong giá»� hĂ ng, táº¡o CartDetail má»›i
			DetailCartResponse detailCart = new DetailCartResponse(productSku.getId(), product.getName(),
					productColorImg.getImage(), productColorImg.getColor().getName(), productSku.getSize().getName(),
					productRequest.getQuantity(), productSku.getPrice());
			detailCarts.add(detailCart);
		}

		updateCartCookie(response, detailCarts);

	}

	// giáº£i mĂ£
	public List<DetailCartResponse> decodeCartDetailsFromCookie(String cartCookieValue) {
		Gson gson = new Gson();
		List<DetailCartResponse> detailCarts = new ArrayList<>();

		try {
			// Giáº£i mĂ£ chuá»—i JSON tá»« cookie (Base64)
			byte[] decodedBytes = Base64.getDecoder().decode(cartCookieValue);
			String decodedCartJson = new String(decodedBytes, StandardCharsets.UTF_8);

			// Chuyá»ƒn Ä‘á»•i chuá»—i JSON thĂ nh danh sĂ¡ch DetailCartResponse sá»­ dá»¥ng
			// Gson
			detailCarts = gson.fromJson(decodedCartJson, new TypeToken<List<DetailCartResponse>>() {
			}.getType());

		} catch (Exception e) {
			throw new RuntimeException("Error parsing cart details from cookie", e);
		}

		return detailCarts;
	}

	// PhÆ°Æ¡ng thá»©c cáº­p nháº­t giá»� hĂ ng trong cookie
	public void updateCartCookie(HttpServletResponse response, List<DetailCartResponse> detailCarts) {
		Gson gson = new Gson();
		try {
			// Chuyá»ƒn danh sĂ¡ch CartDetail thĂ nh chuá»—i JSON
			String cartDetailsJson = gson.toJson(detailCarts);
			// MĂ£ hĂ³a chuá»—i JSON thĂ nh Base64
			String encodedCartDetails = Base64.getEncoder()
					.encodeToString(cartDetailsJson.getBytes(StandardCharsets.UTF_8));

			// Táº¡o cookie vá»›i thá»�i gian sá»‘ng lĂ  1 tuáº§n (7 ngĂ y)
			Cookie cartCookie = new Cookie("cart", encodedCartDetails);
			cartCookie.setHttpOnly(true); // NgÄƒn cháº·n truy cáº­p tá»« Javascript
			cartCookie.setSecure(true); // Chá»‰ gá»­i cookie qua HTTPS
			cartCookie.setPath("/"); // Ä�áº·t Ä‘Æ°á»�ng dáº«n mĂ  cookie cĂ³ hiá»‡u lá»±c
			cartCookie.setMaxAge(7 * 24 * 60 * 60); // Thá»�i gian sá»‘ng lĂ  7 ngĂ y

			// ThĂªm cookie vĂ o response
			response.addCookie(cartCookie);

		} catch (Exception e) {
			throw new RuntimeException("Error converting CartDetail list to JSON", e);
		}
	}

	// PhÆ°Æ¡ng thá»©c láº¥y ra giá»� hĂ ng ngÆ°á»�i dĂ¹ng
	private Cart getUserCart(User user) {
		// NgÆ°á»�i dĂ¹ng chÆ°a Ä‘Äƒng nháº­p
		if (user == null) {
			return null;
		}
		String phone = user.getPhone();
		// láº¥y ra cart
		Cart cart = cartRepository.getUserCartByPhone(phone)
				.orElseThrow(() -> new RuntimeException("Not found cart for user"));
		return cart;
	}

	// PhÆ°Æ¡ng thá»©c láº¥y giá»� hĂ ng tá»« trong Database
	private List<DetailCartResponse> getCartDetailsFromDatabase(Cart cart) {
		List<DetailCartResponse> detailCartResponses = cartDetailRepository.getDetailCartByCartId(cart.getId());
		return detailCartResponses;
	}

	// PhÆ°Æ¡ng thá»©c Ä‘á»ƒ láº¥y chi tiáº¿t giá»� hĂ ng tá»« cookie trong servlet
	private List<DetailCartResponse> getCartDetailsFromCookie(HttpServletRequest request) {
		// Táº¡o danh sĂ¡ch Ä‘á»ƒ chá»©a chi tiáº¿t giá»� hĂ ng
		List<DetailCartResponse> detailCartResponses = new ArrayList<>();

		// Láº¥y giĂ¡ trá»‹ cookie giá»� hĂ ng theo tĂªn "cart"
		String cartCookieValue = getCookieValue(request, "cart");

		if (cartCookieValue != null) {
			Gson gson = new Gson();
			try {
				// Giáº£i mĂ£ chuá»—i Base64 tá»« cookie
				byte[] decodedBytes = Base64.getDecoder().decode(cartCookieValue);
				String decodedCartJson = new String(decodedBytes);

				// Chuyá»ƒn Ä‘á»•i JSON thĂ nh List<DetailCartResponse> sá»­ dá»¥ng Gson
				List<DetailCartResponse> cartDetails = gson.fromJson(decodedCartJson,
						new TypeToken<List<DetailCartResponse>>() {
						}.getType());
				// ThĂªm cĂ¡c chi tiáº¿t giá»� hĂ ng tá»« cookie vĂ o danh sĂ¡ch
				detailCartResponses.addAll(cartDetails);
			} catch (Exception e) {
				// Xá»­ lĂ½ lá»—i náº¿u khĂ´ng thá»ƒ giáº£i mĂ£ hoáº·c parse JSON
				throw new RuntimeException("Error parsing cart details from cookies", e);
			}
		}

		return detailCartResponses;
	}

	private List<DetailCartResponse> getSelectProductsFromCookiesForCheckout(String[] selectedCartIds,
			HttpServletRequest request) {
		List<DetailCartResponse> detailCarts = new ArrayList<>();
		List<DetailCartResponse> selectProducts = new ArrayList<>();

		// Láº¥y giĂ¡ trá»‹ cá»§a cookie "cart"
		String cartCookieValue = getCookieValue(request, "cart");

		// Náº¿u cĂ³ cookie vĂ  khĂ´ng rá»—ng, giáº£i mĂ£ cookie thĂ nh danh sĂ¡ch sáº£n
		// pháº©m
		if (cartCookieValue != null && !cartCookieValue.isEmpty()) {
			detailCarts = decodeCartDetailsFromCookie(cartCookieValue);
		}

		// Lá»�c vĂ  thĂªm cĂ¡c sáº£n pháº©m cáº§n checkout vĂ o danh sĂ¡ch
		for (String id : selectedCartIds) {
			// TĂ¬m sáº£n pháº©m tá»« danh sĂ¡ch Ä‘Ă£ cĂ³ tá»« cookie
			Optional<DetailCartResponse> existingDetailCart = detailCarts.stream()
					.filter(cd -> cd.getCartId().equals(Long.parseLong(id))).findFirst();

			// Náº¿u tĂ¬m tháº¥y sáº£n pháº©m, thĂªm vĂ o danh sĂ¡ch
			existingDetailCart.ifPresent(selectProducts::add);
		}

		return selectProducts;
	}

	// PhÆ°Æ¡ng thá»©c láº¥y giĂ¡ trá»‹ tá»« cookie
	public String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}

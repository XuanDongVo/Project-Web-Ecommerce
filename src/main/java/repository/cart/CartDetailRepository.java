package repository.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import dbConnection.DBConnection;
import dto.respository.DetailCartResponse;
import entity.Cart;
import entity.CartDetail;
import entity.ProductSku;

public class CartDetailRepository {
	private Connection connection = null;
	private PreparedStatement pst = null;

	public void removeByProductSkuId(Connection connection, Long productskuId) {
		try {
			String sql = "DELETE FROM cart_detail WHERE product_sku_id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, productskuId);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// láº¥y ra nhá»¯ng sáº£n pháº©m mĂ  khĂ¡ch hĂ ng chá»�n Ä‘á»ƒ checkout
	public List<DetailCartResponse> getSelectProductsForCheckout(String[] selectedCartIds) {
		connection = DBConnection.getConection();
		List<DetailCartResponse> responses = new ArrayList<>();
		try {
			// XĂ¢y dá»±ng chuá»—i cĂ¢u há»�i cho sá»‘ lÆ°á»£ng `selectedCartIds` Ä‘á»ƒ sá»­ dá»¥ng trong cĂ¢u
			// lá»‡nh SQL IN
			StringBuilder placeholders = new StringBuilder();
			for (int i = 0; i < selectedCartIds.length; i++) {
				placeholders.append("?");
				if (i < selectedCartIds.length - 1) {
					placeholders.append(", ");
				}
			}

			// Sá»­ dá»¥ng cĂ¢u truy váº¥n vá»›i IN
			String sql = "SELECT c.id, p.name, pci.image, color.name AS color, size.name AS size, cd.quantity, sku.price "
					+ "FROM cart_detail AS cd " + "INNER JOIN cart AS c ON c.id = cd.cart_id "
					+ "INNER JOIN product_sku AS sku ON sku.id = cd.product_sku_id "
					+ "INNER JOIN size ON size.id = sku.size_id "
					+ "INNER JOIN product_color_img AS pci ON pci.id = sku.product_color_img_id "
					+ "INNER JOIN product AS p ON p.id = pci.product_id "
					+ "INNER JOIN color ON color.id = pci.color_id " + "WHERE c.id IN (" + placeholders + ")";

			pst = connection.prepareStatement(sql);

			// Thiáº¿t láº­p giĂ¡ trá»‹ cho tá»«ng tham sá»‘ trong cĂ¢u lá»‡nh SQL
			for (int i = 0; i < selectedCartIds.length; i++) {
				pst.setString(i + 1, selectedCartIds[i]);
			}

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// Chuyá»ƒn byte[] thĂ nh chuá»—i Base64
				String base64Image = Base64.getEncoder().encodeToString(rs.getBytes(3));

				DetailCartResponse detailCartResponse = new DetailCartResponse(rs.getLong(1), rs.getString(2),
						base64Image, rs.getString(4), rs.getString(5), rs.getInt(6), rs.getDouble(7));
				responses.add(detailCartResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return responses;
	}

	// láº¥y ra sá»‘ lÆ°á»£ng sáº£n pháº©m trong giá»� hĂ ng cá»§a khĂ¡ch hĂ ng
	public int getQuantityProductFromCart(Cart cart) {
		connection = DBConnection.getConection();
		String sql = "select sum(quantity) quantity\r\n" + "from cart_detail\r\n" + "where cart_id = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setLong(1, cart.getId());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	// láº¥y ra CART_DETAIL báº±ng id
	public Optional<CartDetail> findById(Long cartDetailId) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM cart_detail WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, cartDetailId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				CartDetail cartDetail = new CartDetail();
				cartDetail.setId(rs.getLong(1));

				Cart cart = new Cart(rs.getLong(2), null);
				cartDetail.setCart(cart);

				ProductSku productSku = new ProductSku();
				productSku.setId(rs.getLong(3));
				cartDetail.setProductSku(productSku);

				cartDetail.setQuantity(rs.getInt(4));
				return Optional.of(cartDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// láº¥y ra danh sĂ¡ch CART_DETAIL báº±ng cartId
	public List<DetailCartResponse> getDetailCartByCartId(Long cartId) {
		connection = DBConnection.getConection();
		List<DetailCartResponse> responses = new ArrayList<>();
		try {
			String sql = "SELECT  c.id , p.name , pci.image , color.name as color , size.name  as size , cd.quantity , sku.price  FROM cart_detail as cd "
					+ "INNER JOIN cart AS c ON c.id = cd.cart_id "
					+ "INNER JOIN product_sku AS sku ON sku.id = cd.product_sku_id "
					+ "INNER JOIN size ON size.id = sku.size_id "
					+ "INNER JOIN product_color_img AS pci ON pci.id = sku.product_color_img_id "
					+ "INNER JOIN product AS p ON p.id = pci.product_id "
					+ "INNER JOIN color ON color.id = pci.color_id " + "WHERE c.id = ? ";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, cartId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				// Chuyá»ƒn byte[] thĂ nh chuá»—i Base64
				String base64Image = Base64.getEncoder().encodeToString(rs.getBytes(3));

				DetailCartResponse detailCartResponse = new DetailCartResponse(rs.getLong(1), rs.getString(2),
						base64Image, rs.getString(4), rs.getString(5), rs.getInt(6), rs.getDouble(7));
				responses.add(detailCartResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responses;
	}

	// láº¥y ra CART_DETAIL báº±ng productSkuId vĂ  cartId
	public CartDetail findByProductSkuAndCart(Long productSkuId, Long cartId) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM cart_detail WHERE cart_id = ? AND product_sku_id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, cartId);
			pst.setLong(2, productSkuId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				CartDetail cartDetail = new CartDetail();
				cartDetail.setId(rs.getLong(1));

				Cart cart = new Cart(rs.getLong(2), null);
				cartDetail.setCart(cart);

				ProductSku productSku = new ProductSku();
				productSku.setId(rs.getLong(3));
				cartDetail.setProductSku(productSku);

				cartDetail.setQuantity(rs.getInt(4));
				return cartDetail;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// thĂªm sáº£n pháº©m vĂ o trong giá»� hĂ ng
	public void addProductSkuInCartDetail(Long productSkuId, Cart cart, int quantity) {
		connection = DBConnection.getConection();
		try {
			// Táº¯t auto-commit Ä‘á»ƒ thá»±c hiá»‡n giao dá»‹ch thá»§ cĂ´ng
			connection.setAutoCommit(false);

			String sql = "INSERT INTO cart_detail (cart_id ,product_sku_id ,quantity)  VALUES(?,?,?)";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, productSkuId);
			pst.setLong(2, cart.getId());
			pst.setInt(3, quantity);
			pst.executeUpdate();

			// Commit náº¿u má»�i thá»© Ä‘á»�u thĂ nh cĂ´ng
			connection.commit();
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}

			// In ra lá»—i ban Ä‘áº§u
			e.printStackTrace();

		}
	}

	// cáº­p nháº­t sá»‘ lÆ°á»£ng sáº£n phĂ¢m trong giá»� hĂ ng
	public void updateQuantityInCartDetail(CartDetail cartDetail) {
		connection = DBConnection.getConection();
		try {
			// Táº¯t auto-commit Ä‘á»ƒ thá»±c hiá»‡n giao dá»‹ch thá»§ cĂ´ng
			connection.setAutoCommit(false);

			String sql = "UPDATE cart_detail SET quantity = ? WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setInt(1, cartDetail.getQuantity());
			pst.setLong(2, cartDetail.getId());
			pst.executeUpdate();

			// Commit náº¿u má»�i thá»© Ä‘á»�u thĂ nh cĂ´ng
			connection.commit();

		} catch (Exception e) {
			// Rollback giao dá»‹ch náº¿u cĂ³ lá»—i xáº£y ra
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}

			// In ra lá»—i ban Ä‘áº§u
			e.printStackTrace();

		}
	}

	// xĂ³a sáº£n pháº©m trong giá»� hĂ ng táº¡m thá»�i
	public void deleteProductSkuInCartDetail(Long cartDetailId) {
		connection = DBConnection.getConection();
		try {
			// Táº¯t auto-commit Ä‘á»ƒ thá»±c hiá»‡n giao dá»‹ch thá»§ cĂ´ng
			connection.setAutoCommit(false);

			String sql = "DELETE FROM cart_detail WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, cartDetailId);
			pst.executeUpdate();

			// Commit náº¿u má»�i thá»© Ä‘á»�u thĂ nh cĂ´ng
			connection.commit();
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}

			// In ra lá»—i ban Ä‘áº§u
			e.printStackTrace();

		}
	}
}

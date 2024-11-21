package entity;

public class CartDetail {
	private Long id;
	private Cart cart;
	private ProductSku productSku;
	private int quantity;

	public CartDetail() {
		super();
	}

	public CartDetail(Long id, Cart cart, ProductSku productSku, int quantity) {
		super();
		this.id = id;
		this.cart = cart;
		this.productSku = productSku;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}

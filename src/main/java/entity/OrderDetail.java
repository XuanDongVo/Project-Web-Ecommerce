package entity;

public class OrderDetail {
	private Long id;
	private Long orderId;
	private Long productSkuId;
	private double price;
	private int quantity;

	public OrderDetail() {
	}

	public OrderDetail(Long id, Long orderId, Long productSkuId, double price, int quantity) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productSkuId = productSkuId;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}

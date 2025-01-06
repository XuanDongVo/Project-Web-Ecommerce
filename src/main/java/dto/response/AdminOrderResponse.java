package dto.response;

public class AdminOrderResponse {
	private long id_order_admin;
	private String name_customer;
	private String name_product;
	private int quantity_order;
	private double price;
	private String status;
	public AdminOrderResponse(long id_order_admin, String name_customer, String name_product, int quantity_order,
			double price, String status) {
		super();
		this.id_order_admin = id_order_admin;
		this.name_customer = name_customer;
		this.name_product = name_product;
		this.quantity_order = quantity_order;
		this.price = price;
		this.status = status;
	}
	public long getId_order_admin() {
		return id_order_admin;
	}
	public String getName_customer() {
		return name_customer;
	}
	public String getName_product() {
		return name_product;
	}
	public int getQuantity_order() {
		return quantity_order;
	}
	public double getPrice() {
		return price;
	}
	public String getStatus() {
		return status;
	}
	@Override
	public String toString() {
		return "AdminOrderResponse [id_order_admin=" + id_order_admin + ", name_employee=" + name_customer
				+ ", name_product=" + name_product + ", quantity_order=" + quantity_order + ", price=" + price
				+ ", status=" + status + "]";
	}
	
	
}
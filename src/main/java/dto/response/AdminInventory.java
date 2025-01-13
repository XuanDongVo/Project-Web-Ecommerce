package dto.response;

public class AdminInventory {
	private long id;
	private String product_name;
	private String color_name;
	private String size_name;
	private double price;
	private int stock;

	public AdminInventory() {
		super();
	}

	public AdminInventory(long id, String product_name, String color_name, String size_name, double price, int stock) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.color_name = color_name;
		this.size_name = size_name;
		this.price = price;
		this.stock = stock;
	}

	public long getId() {
		return id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public String getColor_name() {
		return color_name;
	}

	public String getSize_name() {
		return size_name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public String toString() {
		return "AdminInventory [id=" + id + ", product_name=" + product_name + ", color_name=" + color_name
				+ ", size_name=" + size_name + ", price=" + price + ", stock=" + stock + "]";
	}

}
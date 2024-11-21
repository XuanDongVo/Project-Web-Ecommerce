package entity;

public class Inventory {
	private Long id;
	private ProductSku productSku;
	private int stock;

	public Inventory(Long id, ProductSku productSku, int stock) {
		this.id = id;
		this.productSku = productSku;
		this.stock = stock;
	}

	public Inventory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}

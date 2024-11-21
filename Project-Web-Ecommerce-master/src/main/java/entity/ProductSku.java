package entity;

public class ProductSku {
	private Long id;
	private ProductColorImage productColorImage;
	private Size size;
	private double price;

	public ProductSku() {
	}

	public ProductSku(Long id, ProductColorImage productColorImage, Size size, double price) {
		this.id = id;
		this.productColorImage = productColorImage;
		this.size = size;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductColorImage getProductColorImage() {
		return productColorImage;
	}

	public void setProductColorImage(ProductColorImage productColorImage) {
		this.productColorImage = productColorImage;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}

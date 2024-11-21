package entity;

public class ProductColorImage {
	private Long id;
	private Product product;
	private Color color;
	private String image;
	
	

	public ProductColorImage() {
		super();
	}

	public ProductColorImage(Long id, Product product, Color color, String image) {
		super();
		this.id = id;
		this.product = product;
		this.color = color;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}

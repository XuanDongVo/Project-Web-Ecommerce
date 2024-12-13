package dto.request;

import java.util.List;
import java.util.Map;

public class AddProductInDatabaseRequest {
	private String name;
	private String subCategory;
	private double price;
	private String description;

	private List<ProductSkuResponse> skus;

	public AddProductInDatabaseRequest() {
	}

	public AddProductInDatabaseRequest(String name, String subCategory, double price, List<ProductSkuResponse> skus,
			String description) {
		this.name = name;
		this.subCategory = subCategory;
		this.price = price;
		this.skus = skus;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public List<ProductSkuResponse> getskus() {
		return skus;
	}

	public void setskus(List<ProductSkuResponse> skus) {
		this.skus = skus;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static class ProductSkuResponse {
		private String color;
		private String image;
		private Map<String, Integer> sizeAndStock;

		public ProductSkuResponse() {
		}

		public ProductSkuResponse(String color, String image, Map<String, Integer> sizeAndStock) {
			super();
			this.color = color;
			this.image = image;
			this.sizeAndStock = sizeAndStock;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Map<String, Integer> getSizeAndStock() {
			return sizeAndStock;
		}

		public void setSizeAndStock(Map<String, Integer> sizeAndStock) {
			this.sizeAndStock = sizeAndStock;
		}

	}
}

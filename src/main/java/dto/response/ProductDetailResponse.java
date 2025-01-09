package dto.response;

import java.util.List;
import java.util.Map;

public class ProductDetailResponse {
	private Long productId;
	private String name;
	private String subCategory;
	private double price;
	private String typeProduct;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private List<ProductSkuResponse> productSkus;

	public ProductDetailResponse() {
	}

	public ProductDetailResponse(Long productId, String name, String subCategory, double price, String typeProduct,
			List<ProductSkuResponse> productSkus) {
		super();
		this.productId = productId;
		this.name = name;
		this.subCategory = subCategory;
		this.price = price;
		this.typeProduct = typeProduct;
		this.productSkus = productSkus;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}

	public Long getId() {
		return productId;
	}

	public void setId(Long id) {
		this.productId = id;
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

	public List<ProductSkuResponse> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSkuResponse> productSkus) {
		this.productSkus = productSkus;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static class ProductSkuResponse {
		private Long productColorImgId;
		private String color;
		private String img;
		private Map<String, Integer> sizeAndStock;

		public ProductSkuResponse() {
		}

		public ProductSkuResponse(Long productColorImgId, String color, String img, Map<String, Integer> sizeAndStock) {
			this.productColorImgId = productColorImgId;
			this.color = color;
			this.img = img;
			this.sizeAndStock = sizeAndStock;
		}

		public Long getProductColorImgId() {
			return productColorImgId;
		}

		public void setProductColorImgId(Long productColorImgId) {
			this.productColorImgId = productColorImgId;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public Map<String, Integer> getSizeAndStock() {
			return sizeAndStock;
		}

		public void setSizeAndStock(Map<String, Integer> sizeAndStock) {
			this.sizeAndStock = sizeAndStock;
		}

	}
}

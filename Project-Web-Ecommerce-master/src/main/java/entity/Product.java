package entity;

public class Product {
	private Long id;
	private String name;
	private String description;
	private SubCategory subCategory;

	public Product() {
		super();
	}

	public Product(Long id, String name, String description, SubCategory subCategory) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.subCategory = subCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

}

package dto.request;

public class AddProductInCartRequest {
	private Long id;
	private String size;
	private int quantity;

	
	public AddProductInCartRequest() {
		super();
	}

	public AddProductInCartRequest(Long id, String size, int quantity) {
		super();
		this.id = id;
		this.size = size;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

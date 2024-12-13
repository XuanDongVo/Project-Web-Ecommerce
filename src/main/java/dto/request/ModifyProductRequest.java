package dto.request;

public class ModifyProductRequest {
	private Long id;
	private int quantity;

	public ModifyProductRequest() {
		super();
	}

	public ModifyProductRequest(Long id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

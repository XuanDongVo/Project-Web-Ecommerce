package entity;

public class Cart {
	private Long id;
	private Long userId;
	
	

	public Cart(Long id, Long userId) {
		super();
		this.id = id;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}

package entity;

public class Size {
	private Long id;
	private String name;
	private SizeType sizeType;

	public Size() {
		super();
	}

	public Size(Long id, String name, SizeType sizeType) {
		super();
		this.id = id;
		this.name = name;
		this.sizeType = sizeType;
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

	public SizeType getSizeType() {
		return sizeType;
	}

	public void setSizeType(SizeType sizeType) {
		this.sizeType = sizeType;
	}

}

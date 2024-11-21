package entity;

import java.time.LocalDate;

public class Gender {
	private Long id;
	private String name;
	private LocalDate createAt;
	
	

	public Gender() {
		super();
	}

	public Gender(Long id, String name, LocalDate createAt) {
		this.id = id;
		this.name = name;
		this.createAt = createAt;
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

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
	}

}

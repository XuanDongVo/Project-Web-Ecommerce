package entity;

import java.time.LocalDate;

public class Category {
	private Long id;
	private String name;
	private LocalDate createAt;
	private Gender gender;

	public Category() {
		super();
	}

	public Category(Long id, String name, LocalDate createAt, Gender gender) {
		super();
		this.id = id;
		this.name = name;
		this.createAt = createAt;
		this.gender = gender;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}

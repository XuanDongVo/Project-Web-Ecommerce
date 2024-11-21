package entity;

import java.time.LocalDate;

public class Role {
	private int id;
	private String nameRole;
	private LocalDate date;

	public Role() {
	}

	public Role(int id, String nameRole, LocalDate date) {
		this.id = id;
		this.nameRole = nameRole;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}

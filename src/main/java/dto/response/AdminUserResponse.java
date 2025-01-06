package dto.response;

public class AdminUserResponse {
	private long user_id;
	private String email;
	private String phone;
	private String user_name;
	private String address;
	private String create_at;
	private String role_name;
	public AdminUserResponse(long user_id, String email, String phone, String user_name, String address, String create_at,
			String role_name) {
		super();
		this.user_id = user_id;
		this.email = email;
		this.phone = phone;
		this.user_name = user_name;
		this.address = address;
		this.create_at = create_at;
		this.role_name = role_name;
	}
	public long getUser_id() {
		return user_id;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getCreate_at() {
		return create_at;
	}
	public String getRole_name() {
		return role_name;
	}
	public String getAddress() {
		return address;
	}
	@Override
	public String toString() {
		return "AdminUserResponse [user_id=" + user_id + ", email=" + email + ", phone=" + phone + ", user_name="
				+ user_name + ", address=" + address + ", create_at=" + create_at + ", role_name=" + role_name + "]";
	}
	

	
	
}
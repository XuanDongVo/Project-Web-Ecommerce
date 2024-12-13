package repository.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import dbConnection.DBConnection;
import entity.User;

public class UserRepository {
	Connection connection = null;
	PreparedStatement pst = null;

	// lấy ra người dùng bằng số điện thoại
	public User getUserByPhone(String phone) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM user WHERE phone = ? ";
			pst = connection.prepareStatement(sql);
			pst.setString(1, phone);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setEmail(rs.getString(2));
				user.setPhone(rs.getString(3));
				user.setAddress(rs.getString(4));
				user.setName(rs.getString(6));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// thay đổi mật khẩu người dùng
	public void changePassword(User user, String password) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);
			String sql = "UPDATE user SET password = ? WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, password);
			pst.setLong(2, user.getId());
			pst.executeUpdate();

			// Commit nếu mọi thứ đều thành công
			connection.commit();

		} catch (Exception e) {
			// Rollback giao dịch nếu có lỗi xảy ra
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}

			// In ra lỗi ban đầu
			e.printStackTrace();

		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// thay đổi thông tin người dùng
	public void changeInformationUser(Long userId, String name, String address) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);

			String sql = "UPDATE user SET name = ?, address = ? WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, address);
			pst.setLong(3, userId);

			pst.executeUpdate();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// thay đổi email người dùng
	public void changeEmailUser(User user, String email) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);

			String sql = "UPDATE user SET email = ? WHERE id = ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, email);
			pst.setLong(2, user.getId());

			pst.executeUpdate();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// kiểm tra tài khoản người dùng tồn tại chưa
	public User isAccountExist(String email, String phone) {
		connection = DBConnection.getConection();
		String sql = "SELECT * FROM user WHERE email = ? OR phone = ? ";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, email);
			pst.setString(2, phone);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setEmail(rs.getString(2));
				user.setPhone(rs.getString(3));
				user.setAddress(rs.getString(4));
				user.setName(rs.getString(6));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Lấy ra email của người dùng
	public List<String> getEmailUser() {
		connection = DBConnection.getConection();
		List<String> lst = new ArrayList<>();

		try {
			String sql = "SELECT email FROM user";
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			// Sử dụng vòng lặp while để lấy tất cả email
			while (rs.next()) {
				String email = rs.getString("email"); // Sử dụng getString và lấy theo tên cột
				lst.add(email); // Thêm email vào danh sách
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lst; // Trả về danh sách email
	}

//		public static void main(String[] args) {
//			System.out.println(new UserRepository().getEmailUser());
//			
//		}

	// Kiểm tra mail tồn tại hay chưa
	public boolean checkUserByEmail(String email) {
		return getEmailUser().contains(email);
	}

	public User getUserByEmail(String email) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM user WHERE email = ? ";
			pst = connection.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setEmail(rs.getString(2));
				user.setPhone(rs.getString(3));
				user.setAddress(rs.getString(4));
				user.setName(rs.getString(6));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void addUserByEmail(String email, String name) {
		// Kết nối cơ sở dữ liệu
		connection = DBConnection.getConection();
		try {
			// SQL query để thêm người dùng mà không cần chỉ định user_id
			String sql = "INSERT INTO user (email, name) VALUES (?, ?)";

			// Chuẩn bị câu lệnh SQL
			pst = connection.prepareStatement(sql);

			// Gán giá trị cho các tham số trong câu lệnh SQL
			pst.setString(1, email); // Gán email vào tham số đầu tiên
			pst.setString(2, name); // Gán tên vào tham số thứ hai

			// Thực thi câu lệnh cập nhật cơ sở dữ liệu
			int rowsAffected = pst.executeUpdate(); // executeUpdate dùng cho INSERT, UPDATE, DELETE

			// Kiểm tra xem có ảnh hưởng đến cơ sở dữ liệu hay không
			if (rowsAffected > 0) {
				System.out.println("User added successfully.");
			} else {
				System.out.println("Failed to add user.");
			}
		} catch (Exception e) {
			// In thông báo lỗi nếu có lỗi
			e.printStackTrace();
		} finally {
			// Đảm bảo đóng kết nối và statement
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean checkUserByPhone(String phone) {
		return getPhoneUser().contains(phone);
	}

	// Lấy ra sdt của ngừi dùng
	public List<String> getPhoneUser() {
		connection = DBConnection.getConection();
		List<String> lst = new ArrayList<>();

		try {
			String sql = "SELECT phone FROM user";
			pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			// Sử dụng vòng lặp while để lấy tất cả phone
			while (rs.next()) {
				String phone = rs.getString("phone"); // Sử dụng getString và lấy theo tên cột
				lst.add(phone); // Thêm phone vào danh sách
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lst; // Trả về danh sách email
	}

	// Thêm sdt user vào database
	public void addUserByPhone(String phone) {
		// Kết nối cơ sở dữ liệu
		connection = DBConnection.getConection();
		try {
			// SQL query để thêm số điện thoại user
			String sql = "INSERT INTO user (phone) VALUES (?)";

			// Chuẩn bị câu lệnh SQL
			pst = connection.prepareStatement(sql);

			// Gán giá trị cho các tham số trong câu lệnh SQL
			pst.setString(1, phone); // Thêm số điện thoại vào cơ sở dữ liệu

			// Thực thi câu lệnh cập nhật cơ sở dữ liệu
			int rowsAffected = pst.executeUpdate(); // executeUpdate dùng cho INSERT, UPDATE, DELETE

			// Kiểm tra xem có ảnh hưởng đến cơ sở dữ liệu hay không
			if (rowsAffected > 0) {
				System.out.println("User added successfully.");
			} else {
				System.out.println("Failed to add user.");
			}
		} catch (Exception e) {
			// In thông báo lỗi nếu có lỗi
			e.printStackTrace();
		} finally {
			// Đảm bảo đóng kết nối và statement
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// thay đổi thông tin người dùng qua số điện thoại
	public void changeInformationUserByPhone(String name, String email, String phone, String address) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);

			String sql = "UPDATE user SET name = ?, address = ?, email = ? WHERE phone = ?";

			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, address);
			pst.setString(3, email);
			pst.setString(4, phone);

			// Thực thi câu lệnh
			int rowsUpdated = pst.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Thông tin người dùng đã được cập nhật thành công!");
			} else {
				System.out.println("Không tìm thấy số điện thoại: " + phone);
			}

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// thay đổi thông tin người dùng qua email
	public void changeInformationUserByEmail(String name, String email, String phone, String address) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);

			String sql = "UPDATE user SET name = ?, address = ?, phone = ? WHERE email = ?";

			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, address);
			pst.setString(3, phone);
			pst.setString(4, email);

			// Thực thi câu lệnh
			int rowsUpdated = pst.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Thông tin người dùng đã được cập nhật thành công!");
			} else {
				System.out.println("Không tìm thấy email: " + email);
			}

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// thay đổi thông tin người dùng qua email
	public void changeInformationUserById(String name, String email, String phone, String address, Long id) {
		connection = DBConnection.getConection();
		try {
			// Tắt auto-commit để thực hiện giao dịch thủ công
			connection.setAutoCommit(false);

			String sql = "UPDATE user SET name = ?, address = ?, phone = ?, email = ? where user_id = ?";

			pst = connection.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, address);
			pst.setString(3, phone);
			pst.setString(4, email);
			pst.setLong(5, id);

			// Thực thi câu lệnh
			int rowsUpdated = pst.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Thông tin người dùng đã được cập nhật thành công!");
			} else {
				System.out.println("Không tìm thấy email: " + email);
			}

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Bật lại auto-commit sau giao dịch
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// lấy ra người dùng bằng số điện thoại
	public User getUserById(Long id) {
		connection = DBConnection.getConection();
		try {
			String sql = "SELECT * FROM user WHERE user_id = ? ";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, id);;
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setEmail(rs.getString(2));
				user.setPhone(rs.getString(3));
				user.setAddress(rs.getString(4));
				user.setName(rs.getString(6));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {

	}
}

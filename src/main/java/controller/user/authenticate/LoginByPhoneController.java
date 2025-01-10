package controller.user.authenticate;

import java.io.IOException;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.cart.CartRepository;
import repository.user.UserRepository;

@WebServlet("/verifyPhoneNumber")
public class LoginByPhoneController extends HttpServlet {
	UserRepository userRepository = new UserRepository();
	CartRepository cartRepository = new CartRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy OTP và số điện thoại từ session
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(24 * 3600); // 24 giờ
		String otp = String.valueOf(session.getAttribute("otp"));
		String phone = (String) "84" + req.getSession().getAttribute("phoneNumber");

		// Lấy mã OTP người dùng nhập vào
		String otpVerify = req.getParameter("otp_verify");
//		int otpVerifyConvert = Integer.parseInt(otpVerify);

		System.out.println(otp);
		System.out.println(phone);
		System.out.println(otpVerify);

		if (otpVerify != null) {
			if (otpVerify.equals(otp)) {
				if (userRepository.checkUserByPhone(phone)) {
					User user = userRepository.getUserByPhone(phone);
					session.setAttribute("user", user);
					session.setAttribute("userId", user.getId());

					// Xác thực thành công, chuyển hướng sang trang chủ
					resp.sendRedirect(req.getContextPath() + "/home");
				} else {
					// Thêm số điện thoại mới vào cơ sở dữ liệu
					userRepository.addUserByPhone(phone);
					User user = userRepository.getUserByPhone(phone);
					session.setAttribute("user", user);
					session.setAttribute("userId", user.getId());
					// tạo cart cho người dùng
					cartRepository.addCartForNewUser(user.getId());
					// Xác thực thành công, chuyển hướng sang trang chủ
					resp.sendRedirect("home");
					return;
				}
			} else {
				System.out.println("Lỗi mã OTP");
				req.setAttribute("errorMessage", "Mã OTP không đúng. Vui lòng thử lại.");
				req.getRequestDispatcher("view/user/verify.jsp").forward(req, resp);
				return;
			}
		} else {
			System.out.println("Lỗi ");
			req.setAttribute("errorMessage", "Vui lòng nhập mã OTP.");
			req.getRequestDispatcher("view/user/verify.jsp").forward(req, resp);
		}
	}
}
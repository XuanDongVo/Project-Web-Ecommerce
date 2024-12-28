package controller.user.authenticate;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.user.UserRepository;
import service.stringeeCall.StringeeCallService;

@WebServlet("/sendOtp")
public class SendOtp extends HttpServlet {
	UserRepository userRespository = new UserRepository();
	StringeeCallService stringee = new StringeeCallService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	// Xử lý POST request từ form gửi OTP
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String phoneNumber = request.getParameter("phoneNumber");
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(24 * 3600); // 24 giờ

		try {
			// Tạo OTP
			int otp = stringee.generateRandomOtp();
			String otpConvertString = stringee.convertOtpToWords(otp);

			// Gửi OTP qua Stringee
			StringeeCallService.makeCall(phoneNumber, otpConvertString);
			System.out.println(otpConvertString);

			// Lưu OTP và số điện thoại vào session
			session.setAttribute("otp", otp);
			session.setAttribute("phoneNumber", phoneNumber);

			response.sendRedirect("view/user/verify.jsp"); // Chuyển hướng sang trang xác thực
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error sending OTP: " + e.getMessage());
		}
	}
}
package controller.user.google;

import java.io.IOException;
import java.util.List;

import dto.response.GooglePojo;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.cart.CartRepository;
import repository.user.UserRepository;
import utils.GoogleUtils;

@WebServlet("/login-google")
public class LoginGoogleController extends HttpServlet {
	private UserRepository userRepository = new UserRepository();
	private CartRepository cartRepository = new CartRepository();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(24 * 3600); // 24 hours

		if (code == null || code.isEmpty()) {
			req.getRequestDispatcher("view/user/login.jsp").forward(req, resp);
			return; // Ensure no further processing after forwarding
		}

		String accessToken = GoogleUtils.getToken(code);
		GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

		if (googlePojo.getEmail() != null) {
			if (!userRepository.checkUserByEmail(googlePojo.getEmail())) {
				userRepository.addUserByEmail(googlePojo.getEmail(), googlePojo.getName());
				User user = userRepository.getUserByEmail(googlePojo.getEmail());
				cartRepository.addCartForNewUser(user.getId());
				session.setAttribute("userId", userRepository.getUserByEmail(googlePojo.getEmail()).getId());
				System.out.println(userRepository.getUserByEmail(googlePojo.getEmail()).getId());
			}
			User user = userRepository.getUserByEmail(googlePojo.getEmail());
			session.setAttribute("user", user);
		}

		// Ensure correct redirect after successful login
		resp.sendRedirect(req.getContextPath()+"/home");
	}
}
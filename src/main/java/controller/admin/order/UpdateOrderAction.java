package controller.admin.order;

import java.io.IOException;

import dto.response.AdminOrderResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.order.OrderRepository;
import service.mail.MailService;
@WebServlet("/update_order_action")
public class UpdateOrderAction extends HttpServlet {
    private OrderRepository orderRepository = new OrderRepository();
    private MailService mailService = new MailService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        String status = req.getParameter("orderStatus");

        if (orderId != null && !orderId.isEmpty()) {
            try {
                Long idConvert = Long.parseLong(orderId);
                String customerEmail = orderRepository.getEmailByOrderId(idConvert); // Lấy email khách hàng

                // Cập nhật trạng thái đơn hàng
                orderRepository.updateOrderById(idConvert, status);
                if(customerEmail != null) {
                     if (status.equals("�?ã giao")) {
                        mailService.sendEmail(
                            customerEmail,
                            "�?ơn hàng của bạn đã được giao",
                            "Cảm ơn bạn đã mua hàng! �?ơn hàng của bạn đã được giao thành công."
                        );
                    } else if (status.equals("�?ã hủy")) {
                        mailService.sendEmail(
                            customerEmail,
                            "�?ơn hàng của bạn đã bị hủy",
                            "Rất tiếc, đơn hàng của bạn đã bị hủy. Xin liên hệ hỗ trợ nếu có thắc mắc."
                        );
                    }
                }
               

                resp.sendRedirect(req.getContextPath() + "/order");
            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID đơn hàng không hợp lệ.");
                resp.sendRedirect(req.getContextPath() + "/order");
            }
        } else {
            req.setAttribute("error", "Không có ID đơn hàng để xử lý.");
            resp.sendRedirect(req.getContextPath() + "/order");
        }
    }
}
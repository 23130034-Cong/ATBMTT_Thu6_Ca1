package controller;

import java.io.IOException;
iimport jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/AdminDashboardController")
public class AdminDashboardController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Bảo mật cơ bản: Kiểm tra xem có đúng Admin đăng nhập không
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }

        // Thống kê doanh thu, đơn hàng gửi lên Dashboard
        request.getRequestDispatcher("/AdminDashboard.jsp").forward(request, response);
    }
}
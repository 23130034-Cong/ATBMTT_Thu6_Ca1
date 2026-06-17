package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ForgotPasswordController")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String step = request.getParameter("step");

        if ("confirm".equals(step)) {
            request.getRequestDispatcher("/Confirm.jsp").forward(request, response);
        } else if ("newpassword".equals(step)) {
            request.getRequestDispatcher("/CreateNewPassword.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/ForgotPassword.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("sendEmail".equals(action)) {
            String email = request.getParameter("email");
            // Gửi OTP qua Email tại đây...

            // Chuyển sang trang nhập mã xác nhận
            response.sendRedirect(request.getContextPath() + "/ForgotPasswordController?step=confirm");
        } else if ("verifyOTP".equals(action)) {
            String otp = request.getParameter("otp");
            // Kiểm tra mã OTP đúng hay sai...

            // Đúng OTP -> Chuyển sang trang đặt mật khẩu mới
            response.sendRedirect(request.getContextPath() + "/ForgotPasswordController?step=newpassword");
        } else if ("updatePassword".equals(action)) {
            String newPassword = request.getParameter("newPassword");
            // Cập nhật mật khẩu mới vào cơ sở dữ liệu...

            // Xong xuôi quay lại trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/LoginController");
        }
    }
}
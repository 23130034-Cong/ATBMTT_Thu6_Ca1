package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SavePublicKeyController")
public class SavePublicKeyController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Thay đổi thông tin cấu hình kết nối Database của bạn tại đây
    private static final String DB_URL = "jdbc:mysql://localhost:3306/QL_KHACHHANG?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; // Mật khẩu MySQL của bạn

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu Public Key gửi từ AJAX
        String publicKey = request.getParameter("publicKey");

        // Lấy CustomerID của người dùng đang đăng nhập từ Session
        HttpSession session = request.getSession();

        // GIẢ ĐỊNH: Bạn lưu ID người dùng trong Session với tên "userId" khi họ đăng nhập.
        // Nếu bạn đặt tên khác (ví dụ: "customer", "user_id"), hãy đổi lại cho khớp.
        Integer userId = (Integer) session.getAttribute("UserID");

        // Đoạn code test nhanh: Nếu chưa làm chức năng đăng nhập, bạn có thể gán cứng id = 1 để test giao diện
        if (userId == null) {
            userId = 1;
        }

        if (publicKey == null || publicKey.trim().isEmpty()) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Public Key không được trống!\"}");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Nạp Driver MySQL (Nếu dùng Tomcat cũ cần dòng này, Tomcat mới có thể bỏ qua)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Câu lệnh SQL chèn vào bảng UserKeys dựa theo đúng cấu trúc Database bạn gửi
            String sql = "INSERT INTO UserKeys (UserID, PublicKey, Algorithm, status) VALUES (?, ?, 'RSA', 'Active')";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, publicKey);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                // Phản hồi định dạng JSON báo thành công về cho AJAX ở Client
                response.getWriter().write("{\"status\":\"success\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Không thể lưu dữ liệu vào bảng!\"}");
            }

        } catch (ClassNotFoundException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Thiếu Driver JDBC MySQL!\"}");
            e.printStackTrace();
        } catch (SQLException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Lỗi Database: " + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            // Đóng các kết nối giải phóng tài nguyên
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
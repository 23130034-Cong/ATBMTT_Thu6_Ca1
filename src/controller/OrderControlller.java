package com.yourproject.controller;

import com.yourproject.util.OrderHashService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/processOrder")
public class OrderController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy UserID từ session (đã xác thực đăng nhập)
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        //  Lấy dữ liệu từ form (giả định đã parse thành các đối tượng cần thiết)
        // Lưu ý: Dữ liệu ở đây phải được validate kỹ
        double total = Double.parseDouble(request.getParameter("total"));
        String coupon = request.getParameter("coupon");
        // Giả sử có hàm lấy cartItems từ DB hoặc Session
        List<Map<String, Object>> cartItems = getCartItems(userId);

        //  Lấy KeyID đang Active từ DB (Cực kỳ quan trọng để bảo mật)
        int keyId = db.getActiveKeyId(userId);
        String publicKey = db.getPublicKey(keyId);

        //  Lấy thời gian từ Server (chống Replay Attack)
        long serverTimestamp = System.currentTimeMillis();

        //  Tạo chuỗi Canonical và Hash (Sử dụng service bạn đã sửa)
        String canonicalData = OrderHashService.createCanonicalString(
                userId, cartItems, total, coupon, serverTimestamp, keyId);
        String hash = OrderHashService.hashOrderData(canonicalData);

        //  Ký số bằng Private Key (Lưu ý: Private Key không nên nằm ở code server!)
        // Tốt nhất là ký tại Client bằng WebCrypto API hoặc thông qua HSM
        String signature = signWithPrivateKey(hash, userId);

        //  Lưu đơn hàng vào DB
        db.saveOrder(userId, keyId, signature, serverTimestamp, total);

        //  Ghi Audit Log
        db.logAction(userId, "CREATE_ORDER", "Success");

        response.getWriter().write("Order created and signed successfully!");
    }
}
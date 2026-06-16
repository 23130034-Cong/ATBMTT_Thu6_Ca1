package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrderHashService {

    public static String createCanonicalString(int userId, List<Map<String, Object>> cartItems,
                                               double totalPrice, String couponCode,
                                               long serverTimestamp, int keyId) {
        StringBuilder sb = new StringBuilder();
        // Bổ sung keyId và timestamp vào chuỗi băm
        sb.append("key_id:").append(keyId).append("|");
        sb.append("timestamp:").append(serverTimestamp).append("|");
        sb.append("user_id:").append(userId).append("|");

        // Sắp xếp (Code cũ của bạn giữ nguyên)
        cartItems.sort(Comparator.comparingInt(item -> (int) item.get("product_id")));

        sb.append("items:[");
        for (int i = 0; i < cartItems.size(); i++) {
            Map<String, Object> item = cartItems.get(i);
            // Định dạng giá tiền cố định 2 chữ số thập phân
            sb.append("{id:").append(item.get("product_id"))
                    .append(",qty:").append(item.get("quantity"))
                    .append(",price:").append(String.format("%.2f", (double)item.get("price"))).append("}");
            if (i < cartItems.size() - 1) sb.append(",");
        }
        sb.append("]|");

        sb.append("total:").append(String.format("%.2f", totalPrice)).append("|");
        sb.append("coupon:").append(couponCode != null && !couponCode.isEmpty() ? couponCode : "NONE");

        return sb.toString();
    }
    // Thêm hàm ký
    public static String sign(String hashValue, PrivateKey privateKey) {
        // Dùng Java Security Signature để ký giá trị hashValue
    }

    // Thêm hàm xác thực
    public static boolean verify(String hashValue, String signature, PublicKey publicKey) {
        // Dùng Java Security Signature để kiểm tra
    }

    public static String hashOrderData(String canonicalString) {
        if (canonicalString == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(canonicalString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
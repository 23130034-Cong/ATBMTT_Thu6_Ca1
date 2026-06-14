package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrderHashService {

    public static String createCanonicalString(int userId, List<Map<String, Object>> cartItems, double totalPrice, String couponCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("user_id:").append(userId).append("|");

        Collections.sort(cartItems, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> item1, Map<String, Object> item2) {
                int id1 = (int) item1.get("product_id");
                int id2 = (int) item2.get("product_id");
                return Integer.compare(id1, id2);
            }
        });

        sb.append("items:[");
        for (int i = 0; i < cartItems.size(); i++) {
            Map<String, Object> item = cartItems.get(i);
            sb.append("{id:").append(item.get("product_id"))
                    .append(",qty:").append(item.get("quantity"))
                    .append(",price:").append(item.get("price")).append("}");
            if (i < cartItems.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]|");

        sb.append("total:").append(totalPrice).append("|");
        sb.append("coupon:").append(couponCode != null && !couponCode.trim().isEmpty() ? couponCode : "NONE");

        return sb.toString();
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
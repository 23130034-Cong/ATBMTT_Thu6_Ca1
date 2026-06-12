package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrderHashService {

    /**
     * B1: Chuẩn hóa dữ liệu đơn hàng thành một chuỗi String duy nhất (Canonical Form).
     * Phải sắp xếp danh sách sản phẩm theo ID để đảm bảo dữ liệu luôn đồng nhất.
     */
    public static String createCanonicalString(int userId, List<Map<String, Object>> cartItems, double totalPrice, String couponCode) {
        StringBuilder sb = new StringBuilder();

        // Thêm thông tin người mua hàng
        sb.append("user_id:").append(userId).append("|");

        // Sắp xếp các sản phẩm trong giỏ hàng theo ID sản phẩm tăng dần
        // Tránh trường hợp cùng giỏ hàng nhưng thứ tự thêm vào khác nhau làm sai mã băm
        Collections.sort(cartItems, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> item1, Map<String, Object> item2) {
                int id1 = (int) item1.get("product_id");
                int id2 = (int) item2.get("product_id");
                return Integer.compare(id1, id2);
            }
        });

        // Tiến hành gom chuỗi danh sách sản phẩm
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

        // Thêm thông tin giá tổng và mã giảm giá
        sb.append("total:").append(totalPrice).append("|");
        sb.append("coupon:").append(couponCode != null ? couponCode : "NONE");

        return sb.toString();
    }

    /**
     * B2: Băm chuỗi dữ liệu đã chuẩn hóa bằng thuật toán SHA-256
     */
    public static String hashOrderData(String canonicalString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(canonicalString.getBytes(StandardCharsets.UTF_8));

            // Chuyển mảng byte sang dạng chuỗi Hex (Hexadecimal)
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
            throw new RuntimeException("Lỗi hệ thống: Không tìm thấy thuật toán SHA-256", e);
        }
    }

    // Hàm main dùng để test chạy thử thuật toán độc lập
    public static void main(String[] args) {
        // Giả lập giỏ hàng gồm 2 sản phẩm
        java.util.ArrayList<Map<String, Object>> mockCart = new java.util.ArrayList<>();

        Map<String, Object> item1 = new java.util.HashMap<>();
        item1.put("product_id", 102);
        item1.put("quantity", 2);
        item1.put("price", 150000.0);

        Map<String, Object> item2 = new java.util.HashMap<>();
        item2.put("product_id", 101);
        item2.put("quantity", 1);
        item2.put("price", 200000.0);

        mockCart.add(item1);
        mockCart.add(item2);

        String canonical = createCanonicalString(99, mockCart, 500000.0, "GIAM20");
        String hash = hashOrderData(canonical);

        System.out.println("====== TEST ĐỒ ÁN BẢO MẬT ======");
        System.out.println("Chuỗi chuẩn hóa (Canonical String):");
        System.out.println(canonical);
        System.out.println("\nMã băm thu được (SHA-256 Hash):");
        System.out.println(hash);
    }
}
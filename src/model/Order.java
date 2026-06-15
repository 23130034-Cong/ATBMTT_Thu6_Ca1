package model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private int keyId;               // CẢI TIẾN: Xác định đơn hàng ký bằng khóa nào
    private double totalPrice;
    private String status;
    private String orderDataHash;    // CẢI TIẾN: Lưu mã băm gốc để đối chiếu nhanh
    private String signature;
    private Timestamp createdAt;

    public Order() {}

    public int getKeyId() { return keyId; }
    public void setKeyId(int keyId) { this.keyId = keyId; }

    public String getOrderDataHash() { return orderDataHash; }
    public void setOrderDataHash(String orderDataHash) { this.orderDataHash = orderDataHash; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
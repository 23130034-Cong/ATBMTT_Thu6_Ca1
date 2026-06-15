package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Order;
import util.DBConnection;

public class SecureOrderDAO {
    public boolean insertSecureOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, key_id, total_price, status, order_data_hash, signature, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getKeyId());
            ps.setDouble(3, order.getTotalPrice());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getOrderDataHash());
            ps.setString(6, order.getSignature());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
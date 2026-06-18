package dao;

import util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDAO {
    public boolean checkLogin(String username, String inputPassword) {
        String sql = "SELECT PasswordHash FROM Customers WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPw = rs.getString("PasswordHash");
                return BCrypt.checkpw(inputPassword, hashedPw); // So khớp mật khẩu
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
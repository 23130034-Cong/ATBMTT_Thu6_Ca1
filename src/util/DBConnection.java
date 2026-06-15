package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ql_khachhang?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver JDBC! Hãy kiểm tra lại file .jar");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối đến MySQL thất bại! Kiểm tra URL, Username hoặc Password.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection testConn = DBConnection.getConnection();
        if (testConn != null) {
            try {
                testConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
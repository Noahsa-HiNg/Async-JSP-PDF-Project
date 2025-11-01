package com.laptrinhmang.asyncapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

  
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver cho MySQL 8+
    private static final String DB_URL = "jdbc:mysql://localhost:3306/async_pdf_project"; // Tên database
    private static final String DB_USER = "root"; // Username của bạn
    private static final String DB_PASS = ""; // Password của bạn
    
    /**
     * Phương thức tĩnh (static) để lấy kết nối đến cơ sở dữ liệu.
     * @return Một đối tượng Connection.
     * @throws SQLException nếu kết nối thất bại.
     */
    public static Connection getConnection() throws SQLException {
        
        /*
         * Từ JDBC 4.0 (Java 6), bước đăng ký Driver (Class.forName) 
         * là không bắt buộc vì Driver sẽ tự động được đăng ký 
         * khi thư viện .jar (mysql-connector) được tải.
         * * Tuy nhiên, nếu bạn gặp lỗi "No suitable driver found", 
         * hãy bỏ comment dòng dưới đây:
         */
        // try {
        //     Class.forName(DB_DRIVER);
        // } catch (ClassNotFoundException e) {
        //     e.printStackTrace();
        //     throw new SQLException("MySQL JDBC Driver not found!", e);
        // }

        // Thực hiện kết nối
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // (Tùy chọn) Bạn có thể viết thêm một phương thức main để test kết nối
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối Database thành công!");
                System.out.println("Schema: " + conn.getCatalog());
            } else {
                System.out.println("Kết nối Database thất bại!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
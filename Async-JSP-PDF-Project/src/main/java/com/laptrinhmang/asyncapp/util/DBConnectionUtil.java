package com.laptrinhmang.asyncapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {
    
    private static Properties properties = new Properties();
    static {
        try (InputStream input = DBConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            
            if (input == null) {
                System.err.println("Lỗi: Không tìm thấy file 'db.properties' trong thư mục resources!");
            } else {

                properties.load(input);
                String driverClass = properties.getProperty("db.driver");
                if (driverClass != null) {
                    Class.forName(driverClass);
                } else {

                     Class.forName("com.mysql.cj.jdbc.Driver");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khởi tạo cấu hình Database: " + e.getMessage());
        }
    }
    // ------------------------

    public static Connection getConnection() throws SQLException {

        String DB_URL = properties.getProperty("db.url");
        String DB_USER = properties.getProperty("db.username");
        String DB_PASS = properties.getProperty("db.password");

        if (DB_URL == null) {
             throw new SQLException("Không đọc được db.url từ file properties. Kiểm tra lại tên file hoặc nội dung.");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    public static String getUploadDir() {
        return properties.getProperty("storage.upload.dir");
    }

    public static String getResultDir() {
        return properties.getProperty("storage.result.dir");
    }

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
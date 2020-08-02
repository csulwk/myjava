package com.lwk.myspring.util;

import java.sql.*;

/**
 * @author kai
 * @date 2020-08-01 9:55
 */
public class JdbcUtil {
    private static String url= "jdbc:hive2://192.168.99.100:10000/test";
    private static String user ="root";
    private static String password="123456";
    private static String driver = "org.apache.hive.jdbc.HiveDriver";

    /**
     * 初始化
     */
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     * @param statement
     * @param connection
     */
    public static void close(Statement statement, Connection connection){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     * @param result
     * @param statement
     * @param connection
     */
    public static void close(ResultSet result, Statement statement, Connection connection){
        if (result != null){
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(statement, connection);
    }
}

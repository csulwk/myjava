package com.lwk.myspring.hive.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author kai
 * @date 2020-08-02 14:06
 */
@Slf4j
public class HiveDataSource {

    private static DruidDataSource hiveDataSource = new DruidDataSource();
    private static Connection conn = null;

    private static String url= "jdbc:hive2://192.168.99.100:10000/test";
    private static String username = "root";
    private static String password = "123456";
    private static int initialSize = 20;
    private static int minIdle = 20;
    private static int maxActive = 500;
    private static int maxWait = 60000;

    private static DruidDataSource getHiveDataSource() {

        if(hiveDataSource.isInited()){
            return hiveDataSource;
        }

        try {
            // 基本属性 url、user、password
            hiveDataSource.setUrl(url);
            hiveDataSource.setUsername(username);
            hiveDataSource.setPassword(password);

            // 配置初始化大小、最小、最大
            hiveDataSource.setInitialSize(initialSize);
            hiveDataSource.setMinIdle(minIdle);
            hiveDataSource.setMaxActive(maxActive);

            // 配置获取连接等待超时的时间
            hiveDataSource.setMaxWait(maxWait);

            // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            hiveDataSource.setTimeBetweenEvictionRunsMillis(60000);

            // 配置一个连接在池中最小生存的时间，单位是毫秒
            hiveDataSource.setMinEvictableIdleTimeMillis(300000);

            // hiveDataSource.setValidationQuery("select * from xxxx");
            hiveDataSource.setTestWhileIdle(false);
            // hiveDataSource.setTestOnBorrow(false);
            // hiveDataSource.setTestOnReturn(false);

            // 打开PSCache，并且指定每个连接上PSCache的大小
            hiveDataSource.setPoolPreparedStatements(true);
            hiveDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

            // 配置监控统计拦截的filters
            // hiveDataSource.setFilters("stat");

            hiveDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
            closeHiveDataSource();
        }
        return hiveDataSource;
    }

    /**
     * 关闭Hive连接池
     */
    private static void closeHiveDataSource(){
        if(hiveDataSource != null){
            hiveDataSource.close();
        }
    }

    /**
     *
     * 获取Hive连接
     */
    public static Connection getHiveConnection(){
        try {
            hiveDataSource = getHiveDataSource();
            conn = hiveDataSource.getConnection();
        } catch (SQLException e) {
            log.error("--"+e+":获取Hive连接失败！");
        }
        return conn;
    }

    /**
     * 关闭Hive连接
     */
    public static void closeHiveConnection(){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            log.error("--"+e+":关闭Hive-conn连接失败！");
        }
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        String sql = "select * from my_table";
        Connection conn = HiveDataSource.getHiveConnection();
        Statement stmt = null;
        if(conn == null){
            System.out.println("null");
        }else{
            System.out.println("conn");
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            int i = 0;
            while(res.next()){
                if(i<10){
                    System.out.println(res.getString(1));
                    i++;
                }
            }
            stmt.close();
            conn.close();
        }
    }
}

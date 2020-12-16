package com.lwk.myspring.hive.main;

import com.alibaba.fastjson.JSONArray;
import com.lwk.myspring.hive.util.HiveUtil;
import com.lwk.myspring.hive.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hive.jdbc.HiveStatement;

import java.sql.*;
import java.util.List;

/**
 * @author kai
 * @date 2020-08-01 0:15
 */
@Slf4j
public class HiveJdbc {

    public static void main(String[] args) {
        Connection conn = null;
        HiveStatement stmt = null;
        try{
            conn = JdbcUtil.getConnection();
            stmt = (HiveStatement) conn.createStatement();

            //String sql = " use test ; select * from my_table mt left join student_info si on 1= 1;;   select * from student_info  ;";
            String sql = "dfs -ls /";

            List<String> sqlList = HiveUtil.checkSql(sql);
            log.info("sqlList -> {}", sqlList);
            JSONArray json = HiveUtil.query(stmt, sqlList);
            log.info("json -> {}", json);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.close(stmt, conn);
        }
    }

    class LogTask implements Runnable {
        private HiveStatement stmt;
        public LogTask(HiveStatement stmt) {
            this.stmt = stmt;
        }
        @Override
        public void run() {
            try {
                while (stmt.hasMoreLogs()) {
                    try {
                        for (String line : stmt.getQueryLog(true, 1000)) {
                            log.info(line);
                        }
                        Thread.sleep(20);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.lwk.myspring.hive.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.hive.util.HiveUtil;
import com.lwk.myspring.hive.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author kai
 * @date 2020-08-01 9:44
 */
@RestController
@Slf4j
public class HiveController {

    @Autowired
    public DataSource dataSource;

    @PostMapping("/hive/query1")
    public JSONObject queryHive1(@RequestParam(value = "sql", required = true) String sql) throws Exception {
        log.info("HiveController...");

        Connection connection = JdbcUtil.getConnection();
        Statement statement = connection.createStatement();

        List<String> sqlList = HiveUtil.checkSql(sql);
        log.info("sqlList -> {}", sqlList);
        JSONArray json = HiveUtil.query(statement, sqlList);
        log.info("json -> {}", json);

        return new JSONObject().fluentPut("code", "000000").fluentPut("msg", "ok").fluentPut("data", json);
    }
    @PostMapping("/hive/query2")
    public JSONObject queryHive2(@RequestParam(value = "sql", required = true) String sql) throws Exception {
        log.info("HiveController...");

        Connection conn = dataSource.getConnection();
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

        return new JSONObject().fluentPut("code", "000000").fluentPut("msg", "ok");
    }
}

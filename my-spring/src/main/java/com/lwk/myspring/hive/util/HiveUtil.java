package com.lwk.myspring.hive.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kai
 * @date 2020-08-01 10:18
 */
@Slf4j
public class HiveUtil {

    /**
     *
     * @param stmt
     * @param sqlList
     * @return
     * @throws SQLException
     */
    public static JSONArray query(Statement stmt, List<String> sqlList) throws SQLException {
        JSONArray result = new JSONArray();
        ResultSet rs = null;
        for (String sql : sqlList) {
            log.info("================");
            JSONObject obj = new JSONObject();
            if (stmt.execute(sql)) {
                rs = stmt.getResultSet();
                if (rs != null) {
                    ResultSetMetaData rd= rs.getMetaData();
                    StringBuilder colNames = new StringBuilder(" | ");
                    for (int i = 1; i <= rd.getColumnCount(); i++) {
                        colNames.append(rd.getColumnName(i)).append(" | ");
                    }
                    log.info(colNames.toString());
                    obj.put("columnName",colNames.toString().substring(3));
                    List<String> list = new ArrayList<>();
                    while (rs.next()){
                        StringBuilder cols = new StringBuilder(" | ");
                        for (int i = 1; i <= rd.getColumnCount(); i++) {
                            cols.append(rs.getString(i)).append(" | ");
                        }
                        log.info(cols.toString());
                        list.add(cols.toString());
                    }
                    obj.put("columnValue", list);
                }
                result.add(obj);

            }
        }
        return result;
    }

    /**
     *
     * @param sql
     * @return
     */
    public static List<String> checkSql(String sql) {
        List<String> list = new ArrayList<>();
        String[] arr = sql.split(";");
        for (String s : arr) {
            if (!StringUtils.isEmpty(s)) {
                String t = s.trim();
                list.add(t);
                log.info(t);
            }
        }
        return list;
    }
}

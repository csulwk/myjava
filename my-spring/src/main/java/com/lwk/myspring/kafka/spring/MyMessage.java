package com.lwk.myspring.kafka.spring;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义传输数据
 * @author kai
 * @date 2021-02-18 13:50
 */
public class MyMessage {

    private int id;

    private String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public MyMessage() {}
    public MyMessage(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

package com.lwk.myspring.mysql.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kai
 * @date 2020-12-21 22:32
 */
@Getter
@Setter
public class OrderReq {

    private String goodsId;
    private String userId;
    private int count;
    private boolean exp;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

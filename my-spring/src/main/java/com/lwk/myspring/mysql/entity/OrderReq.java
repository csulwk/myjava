package com.lwk.myspring.mysql.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单请求
 * @author kai
 * @date 2020-12-21 22:32
 */
@Getter
@Setter
public class OrderReq {

    private String goodsId;
    private String userId;
    private int count;
    private String type;
    private boolean oExp;
    private boolean gExp;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

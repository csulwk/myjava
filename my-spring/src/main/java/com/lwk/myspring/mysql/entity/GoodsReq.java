package com.lwk.myspring.mysql.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品请求
 * @author kai
 * @date 2020-12-22 23:14
 */
@Getter
@Setter
public class GoodsReq {

    private String goodsId;
    private int count;
    private boolean gExp;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

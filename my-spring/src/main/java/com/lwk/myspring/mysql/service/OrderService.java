package com.lwk.myspring.mysql.service;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.OrderReq;

/**
 * 订单服务
 * @author kai
 * @date 2020-12-21 22:19
 */
public interface OrderService {
    /**
     * 提交订单
     * @param req OrderReq
     * @return JSONObject
     */
    public JSONObject submitOrder(OrderReq req);
}

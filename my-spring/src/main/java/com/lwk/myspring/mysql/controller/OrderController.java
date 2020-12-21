package com.lwk.myspring.mysql.controller;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.OrderReq;
import com.lwk.myspring.mysql.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author kai
 * @date 2020-12-21 21:51
 */
@RestController
@Slf4j
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("submit")
    public JSONObject submitOrder(OrderReq req) {

        JSONObject result = null;
        try {
            result = orderService.submitOrder(req);
        } catch (Exception e) {
            result = new JSONObject().fluentPut("code", "error")
                    .fluentPut("data", e.getMessage());
        }
        return result;
    }

}

package com.lwk.myspring.mysql.controller;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.GoodsStock;
import com.lwk.myspring.mysql.entity.OrderInfo;
import com.lwk.myspring.mysql.entity.OrderReq;
import com.lwk.myspring.mysql.service.GoodsService;
import com.lwk.myspring.mysql.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
    @Autowired
    private GoodsService goodsService;

    @PostMapping("submit")
    public JSONObject submitOrder(OrderReq req) {

        String beMsg = statistic(req);
        JSONObject result = null;
        try {
            result = orderService.submitOrder(req);
        } catch (Exception e) {
            result = new JSONObject().fluentPut("code", "error")
                    .fluentPut("data", e.getMessage());
        }
        String afMsg = statistic(req);

        log.info("\n提交结果：{}\n提交之前：{}\n提交之后：{}", result, beMsg, afMsg);
        return result;
    }

    private String statistic(OrderReq req) {
        List<OrderInfo> orderList = orderService.selectAll();
        GoodsStock goods = goodsService.selectByGoodsId(req.getGoodsId());
        return "总订单数：" + orderList.size() + "；已售出数：" + goods.getGsSoldCount();
    }

}

package com.lwk.myspring;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.controller.OrderController;
import com.lwk.myspring.mysql.entity.OrderReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试类
 * @author kai
 * @date 2020-12-22 21:49
 */
@Slf4j
public class OrderControllerTest extends BaseTest {

    @Autowired
    private OrderController orderController;

    @Test
    public void testGoods() {
        OrderReq req = new OrderReq();
        req.setGoodsId("GID0001");
        req.setUserId("UID01");
        // RandomUtils.nextInt(1, 5);
        req.setCount(1);
        req.setOExp(false);
        req.setGExp(false);
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        req.setType("REQUIRED");

        JSONObject result = orderController.submitOrder(req);
        log.info("result: {}", result);
    }
}

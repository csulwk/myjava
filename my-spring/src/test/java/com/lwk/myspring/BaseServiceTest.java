package com.lwk.myspring;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.OrderInfo;
import com.lwk.myspring.mysql.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 14:34
 */
@Slf4j
public class BaseServiceTest extends BaseTest {

    @Autowired
    private BaseService<OrderInfo, String> baseService;

    @Test
    public void testOrder() {
        List<OrderInfo> order = baseService.selectAll();
        log.info(JSONObject.toJSONString(order));
    }
}

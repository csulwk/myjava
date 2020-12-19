package com.lwk.myspring;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.dao.BaseMapper;
import com.lwk.myspring.mysql.entity.GoodsStock;
import com.lwk.myspring.mysql.entity.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 12:53
 */
@Slf4j
public class BaseMapperTest extends BaseTest {

    @Autowired
    private BaseMapper<OrderInfo, String> orderInfoMapper;
    @Autowired
    private BaseMapper<GoodsStock, Long> goodsStockMapper;

    @Test
    public void testSelect() {
        OrderInfo oi = orderInfoMapper.selectByPrimaryKey("202012191312356313001");
        log.info(JSONObject.toJSONString(oi));
        List<GoodsStock> gs = goodsStockMapper.selectAll();
        log.info(JSONObject.toJSONString(gs));
    }

}

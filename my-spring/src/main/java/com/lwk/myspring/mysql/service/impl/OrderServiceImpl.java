package com.lwk.myspring.mysql.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.dao.GoodsStockMapper;
import com.lwk.myspring.mysql.dao.OrderInfoMapper;
import com.lwk.myspring.mysql.entity.GoodsStock;
import com.lwk.myspring.mysql.entity.OrderInfo;
import com.lwk.myspring.mysql.entity.OrderReq;
import com.lwk.myspring.mysql.service.BaseService;
import com.lwk.myspring.mysql.service.OrderService;
import com.lwk.myspring.mysql.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * 订单服务
 * @author kai
 * @date 2020-12-19 14:27
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseService<OrderInfo, String> implements OrderService {

    private OrderInfoMapper orderInfoMapper;
    private GoodsStockMapper goodsStockMapper;
    @Autowired
    public void setMapper(OrderInfoMapper orderInfoMapper, GoodsStockMapper goodsStockMapper) {
        this.initMapper(orderInfoMapper);
        this.orderInfoMapper = orderInfoMapper;
        this.goodsStockMapper = goodsStockMapper;
    }

    @Override
    public JSONObject submitOrder(OrderReq req) {

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();
        // 生成订单号
        String orderId = IdWorker.getInstance().nextId();
        log.info("orderId: {}", orderId);
        orderInfo.setOiOrderId(orderId);
        orderInfo.setOiGoodsId(req.getGoodsId());
        orderInfo.setOiUserId(req.getUserId());
        orderInfo.setOiCount(req.getCount());
        // 模拟订单金额
        orderInfo.setOiPrice(new BigDecimal(RandomUtils.nextDouble(10, 100)).setScale(2, RoundingMode.HALF_UP));
        orderInfo.setUpdateBy("new");

        // 扣减库存
        int opr = goodsStockMapper.deductStock(req.getGoodsId(), req.getCount());
        if (opr <= 0) {
            throw new RuntimeException("库存不足...");
        }

        // 异常处理
        if (req.isExp()) {
            throw new RuntimeException("抛出异常...");
        }

        // 返回结果
        OrderInfo result = orderInfoMapper.selectByPrimaryKey(orderId);
        return new JSONObject().fluentPut("code", "success").fluentPut("data", result);
    }
}

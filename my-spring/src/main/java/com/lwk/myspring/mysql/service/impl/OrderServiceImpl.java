package com.lwk.myspring.mysql.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.dao.GoodsStockMapper;
import com.lwk.myspring.mysql.dao.OrderInfoMapper;
import com.lwk.myspring.mysql.entity.GoodsReq;
import com.lwk.myspring.mysql.entity.GoodsStock;
import com.lwk.myspring.mysql.entity.OrderInfo;
import com.lwk.myspring.mysql.entity.OrderReq;
import com.lwk.myspring.mysql.service.BaseService;
import com.lwk.myspring.mysql.service.GoodsService;
import com.lwk.myspring.mysql.service.OrderService;
import com.lwk.myspring.mysql.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    @Autowired
    public void setMapper(OrderInfoMapper orderInfoMapper) {
        this.initMapper(orderInfoMapper);
        this.orderInfoMapper = orderInfoMapper;
    }

    @Autowired
    private GoodsService goodsService;
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.REQUIRED)
    @Override
    public JSONObject submitOrder(OrderReq req) {
        // 提交订单
        int cnt = orderInfoMapper.insertSelective(assemblyOrderInfo(req));
        if (cnt <= 0) {
            log.warn("订单错误...");
            throw new RuntimeException("订单错误...");
        }

        // 扣减库存
        JSONObject resp;
        switch(req.getType()){
            case "REQUIRED" :
                resp = goodsService.goodsRequired(assemblyGoodsReq(req));
                break;
            case "SUPPORTS" :
                resp = goodsService.goodsSupports(assemblyGoodsReq(req));
                break;
            case "MANDATORY" :
                resp = goodsService.goodsMandatory(assemblyGoodsReq(req));
                break;
            case "REQUIRES_NEW" :
                resp = goodsService.goodsRequiresNew(assemblyGoodsReq(req));
                break;
            case "NOT_SUPPORTED" :
                resp = goodsService.goodsNotSupported(assemblyGoodsReq(req));
                break;
            case "NEVER" :
                resp = goodsService.goodsNever(assemblyGoodsReq(req));
                break;
            case "NESTED" :
                resp = goodsService.goodsNested(assemblyGoodsReq(req));
                break;
            default :
                log.warn("类型错误...");
                throw new RuntimeException("类型错误...");
        }
        log.info("goods resp: {}", resp);

        // 异常处理
        if (req.isOExp()) {
            log.warn("抛出异常...");
            throw new NullPointerException("抛出异常...");
        }
        return new JSONObject().fluentPut("code", "success");
    }

    @Autowired
    private GoodsStockMapper goodsStockMapper;
    /**
     * 私有方法处理商品库存
     * @param req OrderReq
     */
    private void deductStock(OrderReq req) {
        // 扣减库存
        int opr = goodsStockMapper.deductStock(req.getGoodsId(), req.getCount());
        if (opr <= 0) {
            log.warn("库存不足...");
            throw new RuntimeException("库存不足...");
        }
    }

    private OrderInfo assemblyOrderInfo(OrderReq req) {
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
        return orderInfo;
    }

    private GoodsReq assemblyGoodsReq(OrderReq req) {
        GoodsReq goodsReq = new GoodsReq();
        goodsReq.setGoodsId(req.getGoodsId());
        goodsReq.setCount(req.getCount());
        goodsReq.setGExp(req.isGExp());
        return goodsReq;
    }
}

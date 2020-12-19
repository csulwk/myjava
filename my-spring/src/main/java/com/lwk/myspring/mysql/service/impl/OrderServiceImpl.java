package com.lwk.myspring.mysql.service.impl;

import com.lwk.myspring.mysql.dao.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author kai
 * @date 2020-12-19 14:27
 */
@Service
public class OrderServiceImpl<OrderInfo, String> extends BaseServiceImpl {

    private OrderInfoMapper<OrderInfo, String> orderInfoMapper;
    @Autowired
    public void setMapper(OrderInfoMapper orderInfoMapper) {
        super.setBaseMapper(orderInfoMapper);
        this.orderInfoMapper = orderInfoMapper;
    }
}

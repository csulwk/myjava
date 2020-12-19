package com.lwk.myspring.mysql.dao;

import com.lwk.myspring.mysql.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author kai
 * @date 2020-12-19 13:55
 */
@Repository
public interface OrderInfoMapper<OrderInfo, String> extends BaseMapper {
}

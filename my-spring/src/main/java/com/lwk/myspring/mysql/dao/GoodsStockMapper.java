package com.lwk.myspring.mysql.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author kai
 * @date 2020-12-19 13:55
 */
@Repository
public interface GoodsStockMapper<GoodsStock, Long> extends BaseMapper {
}

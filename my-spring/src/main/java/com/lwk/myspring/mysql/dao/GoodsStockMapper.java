package com.lwk.myspring.mysql.dao;

import com.lwk.myspring.mysql.entity.GoodsStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kai
 * @date 2020-12-19 13:55
 */
@Repository
public interface GoodsStockMapper extends BaseMapper<GoodsStock, Long> {

    /**
     * 扣减库存
     * @param goodsId goodsId
     * @param count count
     * @return int
     */
    public int deductStock(@Param("goodsId") String goodsId, @Param("count") int count);

    /**
     * 获取商品库存
     * @param goodsId goodsId
     * @return GoodsStock
     */
    public GoodsStock selectByGoodsId(@Param("goodsId") String goodsId);
}

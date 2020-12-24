package com.lwk.myspring.mysql.service;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.GoodsReq;
import com.lwk.myspring.mysql.entity.GoodsStock;

/**
 * 商品服务
 * @author kai
 * @date 2020-12-22 22:58
 */
public interface GoodsService extends BaseService<GoodsStock, Long> {

    /**
     * goodsRequired
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsRequired(GoodsReq req);

    /**
     * goodsSupports
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsSupports(GoodsReq req);

    /**
     * goodsMandatory
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsMandatory(GoodsReq req);

    /**
     * goodsRequiresNew
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsRequiresNew(GoodsReq req);

    /**
     * goodsNotSupported
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsNotSupported(GoodsReq req);

    /**
     * goodsNever
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsNever(GoodsReq req);

    /**
     * goodsNested
     * @param req GoodsReq
     * @return JSONObject
     */
    public JSONObject goodsNested(GoodsReq req);

    /**
     * selectByGoodsId
     * @param goodsId goodsId
     * @return GoodsStock
     */
    public GoodsStock selectByGoodsId(String goodsId);
}

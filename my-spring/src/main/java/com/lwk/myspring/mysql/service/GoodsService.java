package com.lwk.myspring.mysql.service;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.entity.GoodsReq;

/**
 * 商品服务
 * @author kai
 * @date 2020-12-22 22:58
 */
public interface GoodsService {
    public JSONObject goodsRequired(GoodsReq req);
    public JSONObject goodsSupports(GoodsReq req);
    public JSONObject goodsMandatory(GoodsReq req);
    public JSONObject goodsRequiresNew(GoodsReq req);
    public JSONObject goodsNotSupported(GoodsReq req);
    public JSONObject goodsNever(GoodsReq req);
    public JSONObject goodsNested(GoodsReq req);
}

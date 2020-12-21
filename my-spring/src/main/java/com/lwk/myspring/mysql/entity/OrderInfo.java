package com.lwk.myspring.mysql.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private String oiOrderId;

    private String oiGoodsId;

    private String oiUserId;

    private BigDecimal oiPrice;

    private Integer oiCount;

    private Date createTime;

    private Date updateTime;

    private String updateBy;

    public String getOiOrderId() {
        return oiOrderId;
    }

    public void setOiOrderId(String oiOrderId) {
        this.oiOrderId = oiOrderId;
    }

    public String getOiGoodsId() {
        return oiGoodsId;
    }

    public void setOiGoodsId(String oiGoodsId) {
        this.oiGoodsId = oiGoodsId;
    }

    public String getOiUserId() {
        return oiUserId;
    }

    public void setOiUserId(String oiUserId) {
        this.oiUserId = oiUserId;
    }

    public BigDecimal getOiPrice() {
        return oiPrice;
    }

    public void setOiPrice(BigDecimal oiPrice) {
        this.oiPrice = oiPrice;
    }

    public Integer getOiCount() {
        return oiCount;
    }

    public void setOiCount(Integer oiCount) {
        this.oiCount = oiCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

}

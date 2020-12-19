package com.lwk.myspring.mysql.entity;

import java.util.Date;

public class GoodsStock {
    private Long gsStockId;

    private String gsGoodsId;

    private Integer gsTotalCount;

    private Integer gsSoldCount;

    private Date createTime;

    private Date updateTime;

    private String updateBy;

    public Long getGsStockId() {
        return gsStockId;
    }

    public void setGsStockId(Long gsStockId) {
        this.gsStockId = gsStockId;
    }

    public String getGsGoodsId() {
        return gsGoodsId;
    }

    public void setGsGoodsId(String gsGoodsId) {
        this.gsGoodsId = gsGoodsId;
    }

    public Integer getGsTotalCount() {
        return gsTotalCount;
    }

    public void setGsTotalCount(Integer gsTotalCount) {
        this.gsTotalCount = gsTotalCount;
    }

    public Integer getGsSoldCount() {
        return gsSoldCount;
    }

    public void setGsSoldCount(Integer gsSoldCount) {
        this.gsSoldCount = gsSoldCount;
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
package com.lwk.myspring.mysql.service;

import com.lwk.myspring.mysql.dao.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 14:01
 */
public class BaseService<T, PK extends Serializable> {

    private BaseMapper<T, PK> baseMapper;
    /**
     * 初始化BaseMapper
     * @param baseMapper BaseMapper<T, PK>
     */
    protected void initMapper(BaseMapper<T, PK> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * selectAll
     * @return List<T>
     */
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * selectByPrimaryKey
     * @param id PK
     * @return T
     */
    public T selectByPrimaryKey(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * insertSelective
     * @param entity T
     * @return int
     */
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    /**
     * updateByPrimaryKeySelective
     * @param entity T
     * @return int
     */
    public int updateByPrimaryKeySelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * deleteByPrimaryKey
     * @param id PK
     * @return int
     */
    public int deleteByPrimaryKey(PK id) {
        return baseMapper.deleteByPrimaryKey(id);
    }
}

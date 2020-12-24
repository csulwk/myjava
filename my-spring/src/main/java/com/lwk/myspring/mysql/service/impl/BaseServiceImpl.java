package com.lwk.myspring.mysql.service.impl;

import com.lwk.myspring.mysql.dao.BaseMapper;
import com.lwk.myspring.mysql.service.BaseService;

import java.io.Serializable;
import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 14:01
 */
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    private BaseMapper<T, PK> baseMapper;
    /**
     * 初始化BaseMapper
     * @param baseMapper BaseMapper<T, PK>
     */
    void initMapper(BaseMapper<T, PK> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * selectAll
     * @return List<T>
     */
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * selectByPrimaryKey
     * @param id PK
     * @return T
     */
    @Override
    public T selectByPrimaryKey(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * insertSelective
     * @param entity T
     * @return int
     */
    @Override
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    /**
     * updateByPrimaryKeySelective
     * @param entity T
     * @return int
     */
    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * deleteByPrimaryKey
     * @param id PK
     * @return int
     */
    @Override
    public int deleteByPrimaryKey(PK id) {
        return baseMapper.deleteByPrimaryKey(id);
    }
}

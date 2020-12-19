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
    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return baseMapper.deleteByPrimaryKey(id);
    }
}

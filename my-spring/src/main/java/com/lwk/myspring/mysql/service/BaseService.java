package com.lwk.myspring.mysql.service;

import com.lwk.myspring.mysql.dao.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 14:01
 */
public interface BaseService<T, PK extends Serializable> {

    /**
     * selectAll
     * @return List<T>
     */
    public List<T> selectAll();

    /**
     * selectByPrimaryKey
     * @param id PK
     * @return T
     */
    public T selectByPrimaryKey(PK id);

    /**
     * insertSelective
     * @param entity T
     * @return int
     */
    public int insertSelective(T entity);

    /**
     * updateByPrimaryKeySelective
     * @param entity T
     * @return int
     */
    public int updateByPrimaryKeySelective(T entity);

    /**
     * deleteByPrimaryKey
     * @param id PK
     * @return int
     */
    public int deleteByPrimaryKey(PK id);
}

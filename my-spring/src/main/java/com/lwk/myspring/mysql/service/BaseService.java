package com.lwk.myspring.mysql.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 13:24
 */
public interface BaseService<T, PK extends Serializable> {

    /**
     * selectAll
     * @return List<T>
     */
    public List<T> selectAll();
    /**
     * selectByPrimaryKey
     * @param id PrimaryKey
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
     * @param id PrimaryKey
     * @return int
     */
    public int deleteByPrimaryKey(PK id);
}

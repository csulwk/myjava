package com.lwk.myspring.mysql.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author kai
 * @date 2020-12-19 13:55
 */
public interface BaseMapper<T, PK extends Serializable> {

    /**
     * selectAll
     * @return List<T>
     */
    List<T> selectAll();

    /**
     * deleteByPrimaryKey
     * @param id PrimaryKey
     * @return int
     */
    int deleteByPrimaryKey(PK id);

    /**
     * insert
     * @param record T
     * @return int
     */
    int insert(T record);

    /**
     * insertSelective
     * @param record T
     * @return int
     */
    int insertSelective(T record);

    /**
     * selectByPrimaryKey
     * @param id PK
     * @return T
     */
    T selectByPrimaryKey(PK id);

    /**
     * updateByPrimaryKeySelective
     * @param record T
     * @return int
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * updateByPrimaryKey
     * @param record T
     * @return int
     */
    int updateByPrimaryKey(T record);
}

package com.example.demo.dao;

import com.example.demo.model.active;

public interface activeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(active record);

    int insertSelective(active record);

    active selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(active record);

    int updateByPrimaryKey(active record);
}
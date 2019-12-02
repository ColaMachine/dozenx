package com.dozenx.web.module.interfaceapi.exampletest.dao;


import com.dozenx.web.module.interfaceapi.exampletest.pojo.ExampleTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExampleTestMapper {
    Integer insert(ExampleTest exampleTest);
    ExampleTest selectById(@Param(value = "id") Integer id);
    Integer delete(@Param(value = "id") Integer id);
    Integer update(ExampleTest exampleTest);
    List<ExampleTest> select(Map map);
}

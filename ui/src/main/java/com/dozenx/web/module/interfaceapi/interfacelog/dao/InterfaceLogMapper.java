package com.dozenx.web.module.interfaceapi.interfacelog.dao;


import com.dozenx.web.module.interfaceapi.interfacelog.pojo.InterfaceLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InterfaceLogMapper {
    Integer insert(InterfaceLog interFaceLog);
    InterfaceLog selectById(@Param(value = "id") Integer id);
    List<InterfaceLog> select(Map map);
    Integer delete(@Param(value = "id") Integer id);
    List<InterfaceLog> getListByParams(Map map);
}

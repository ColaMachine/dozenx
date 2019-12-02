package com.dozenx.web.module.interfaceapi.interfaceinfo.dao;

import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InterfaceInfoMapper {
    int insertApi(InterfaceInfo record);

    int deleteApi(@Param(value = "id") Integer id);

    int updateApi(InterfaceInfo record);

    //获取数据
    List<InterfaceInfo> getApiByParams(Map map);//根据传入数据查询接口信息

    InterfaceInfo getApiById(@Param(value = "id") Integer id); //根据id获取接口信息

    Integer insert(InterfaceInfo api);
    List<InterfaceInfo> selectAll();
    List<InterfaceInfo> selectByPrimaryKeyByparams(InterfaceInfo params);
    int insertSelective(InterfaceInfo record);
    int updateByPrimaryKeySelective(InterfaceInfo record);
}

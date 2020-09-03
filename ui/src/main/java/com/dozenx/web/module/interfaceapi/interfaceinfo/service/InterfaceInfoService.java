package com.dozenx.web.module.interfaceapi.interfaceinfo.service;

import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface InterfaceInfoService {
    int insertApi(InterfaceInfo record);
//    int insertParams(InterfaceParams record);

    int deleteApi(Integer id);
//    int deleteParams(Map map);
//    int deleteParamsById(Integer id);
    public List<InterfaceInfo> selectAll();
    int updateApi(InterfaceInfo record);
//    int updateParams(InterfaceParams record);
public List<InterfaceInfo> listByParams(Map map);
    //获取数据
    PageInfo<InterfaceInfo> getApiByParams(Map map, int curPage, int pageSize);//根据传入数据查询接口信息
//    List<InterfaceParams> getParamsById(Integer apiId);//根据接口id获取对应参数信息

    InterfaceInfo getApiById(int id); //根据id获取接口信息

    //接口信息查询，根据项目名称，接口id，版本
    public List<InterfaceInfo> getInterfaceInfo(InterfaceInfo params);
    //接口插入
    int insertSelective(InterfaceInfo record);
    //根据接口id查询参数列表
    List<Map<String,Object>> selectByInterfaceIdParams(int interfaceid);
    //接口修改
    int updateByPrimaryKeySelective(InterfaceInfo record);
}

package com.dozenx.web.module.interfaceapi.interfaceParam.service;


import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;

import java.util.List;

/**
 * @author TTTzzz
 * @create 2019-11-04 15:49
 **/
public interface InterfaceParamService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    int insert(InterfaceParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    int insertSelective(InterfaceParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    InterfaceParam selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(InterfaceParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table interface_params
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Integer key);

    List<InterfaceParam> selectByInterfaceId(int interfaceid);

    int deleteByInterfaceId(int interfaceid);

    int insertParamsList(List<InterfaceParam> params);
}

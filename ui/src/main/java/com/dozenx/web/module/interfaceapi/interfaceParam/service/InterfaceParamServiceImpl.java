package com.dozenx.web.module.interfaceapi.interfaceParam.service;


import com.dozenx.web.module.interfaceapi.interfaceParam.dao.InterfaceParamMapper;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TTTzzz
 * @create 2019-11-04 15:47
 **/
@Service
public class InterfaceParamServiceImpl implements InterfaceParamService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    InterfaceParamMapper paramsMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) { return paramsMapper.deleteByPrimaryKey(id); }

    @Override
    public int insert(InterfaceParam record) { return paramsMapper.insert(record) ;}

    @Override
    public int insertSelective(InterfaceParam record) { return 0; }

    @Override
    public InterfaceParam selectByPrimaryKey(Integer id) {
        return paramsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(InterfaceParam record) { return paramsMapper.updateByPrimaryKeySelective(record); }

    @Override
    public int updateByPrimaryKey(Integer key) { return paramsMapper.updateByPrimaryKey(key); }

    @Override
    public List<InterfaceParam> selectByInterfaceId(int interfaceid) { return paramsMapper.selectByInterfaceId(interfaceid); }

    @Override
    public int deleteByInterfaceId(int interfaceid) {return paramsMapper.deleteByInterfaceId(interfaceid); }

    @Override
    public int insertParamsList(List<InterfaceParam> params) {

        return paramsMapper.insertParamsList(params);
    }

}

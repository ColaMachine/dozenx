package com.dozenx.web.module.interfaceapi.interfacelog.service.Impl;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.module.interfaceapi.interfacelog.dao.InterfaceLogMapper;
import com.dozenx.web.module.interfaceapi.interfacelog.pojo.InterfaceLog;
import com.dozenx.web.module.interfaceapi.interfacelog.service.InterfaceLogService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterfaceLogServiceImpl implements InterfaceLogService {
    private static Logger logger = LoggerFactory.getLogger(InterfaceLogServiceImpl.class);

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    // 获取自动化测试结果
    @Override
    public ResultDTO getTestResult(String params) {
        Map vparams = JsonUtil.toMap(params);
        Integer testId = MapUtils.getInteger(vparams,"testId");
        String startTime = vparams.get("starttime").toString();
        String endTime = vparams.get("endtime").toString();
        Page page = RequestUtil.getPage(vparams);
        Map searchMap = new HashMap();
        if(!StringUtil.isBlank(startTime)){
            searchMap.put("starttime" , DateUtil.fomatDate(startTime));
        }
        if(!StringUtil.isBlank(endTime)){
            searchMap.put("endtime" , DateUtil.fomatDate(endTime));
        }
        if(testId!=null ){
            searchMap.put("testId" , testId);
        }
        if(page !=null){
            searchMap.put("Page" , page);
            searchMap.put("startRow",page.getCurPage()*page.getPageSize()-page.getPageSize());
        }
        List<InterfaceLog> interfaceLogList = new ArrayList<>();
        try {
            interfaceLogList = interfaceLogMapper.select(searchMap);
        }catch (Exception e){
            logger.info("getTestResult error :" +e);
            return ResultUtil.getResult(0,"获取失败");
        }
        if(page!=null) {
            searchMap.remove("Page");
            searchMap.remove("startRow");
        }
        List<InterfaceLog> tempList = interfaceLogMapper.select(searchMap);
        //设置Page参数
        page.setTotalCount(tempList.size());
        page.setTotalPage((tempList.size()%page.getPageSize())==0?(tempList.size()/page.getPageSize()):(tempList.size()/page.getPageSize()+1));
        page.setHasNextPage((page.getTotalPage()!=page.getCurPage())?true:false);
        return ResultUtil.getResult(1,interfaceLogList,"获取成功",page);
    }

    @Override
    public ResultDTO getTestResultByParams(String params) {
        Map<String, Object> vcparams = JsonUtil.toJavaBean(params, Map.class);
        Page page = RequestUtil.getPage(vcparams);
        List<InterfaceLog> interfaceLogList = null;
        com.github.pagehelper.Page<Object> page2 = PageHelper.startPage(page.getCurPage(),page.getPageSize());
        try{
            interfaceLogList = interfaceLogMapper.getListByParams(vcparams);
            logger.info("time:"+vcparams);
        }catch (Exception e){

        }
        logger.info("test:"+page2);
        return ResultUtil.getPageResult(page2);
    }
}

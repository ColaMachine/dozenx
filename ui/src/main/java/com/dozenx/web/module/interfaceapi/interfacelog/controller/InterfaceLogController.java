package com.dozenx.web.module.interfaceapi.interfacelog.controller;

import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.interfaceapi.interfacelog.service.InterfaceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/testLog")
public class InterfaceLogController {
    private static Logger logger = LoggerFactory.getLogger(InterfaceLogController.class);

    @Autowired
    private InterfaceLogService interfaceLogService;

    @API(summary = "获取自动化测试结果", description = "获取自动化测试结果 InterfaceLogController.java:getTestResult",
            parameters = {
                @Param(name = "params",description = "{\"testid\":0,\"starttime\":\"2010-1-1\"" +
                        ",\"endtime\":\"2020-1-1\",\"curPage\":1,\"pageSize\":10}",type = "String",required = true)
            }
    )
    @ResponseBody
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ResultDTO getTestResult(HttpServletRequest request , @RequestParam(name = "params" , required = true) String params){
        return interfaceLogService.getTestResultByParams(params);//interfaceLogService.getTestResult(params);
    }

}

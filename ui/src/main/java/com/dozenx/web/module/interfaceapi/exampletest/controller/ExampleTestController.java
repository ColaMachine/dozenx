package com.dozenx.web.module.interfaceapi.exampletest.controller;


import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.interfaceapi.exampletest.dao.ExampleTestMapper;
import com.dozenx.web.module.interfaceapi.exampletest.service.ExampleTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@APIs(description = "API查询接口")
@RestController
@RequestMapping(value = {"/exampleTest"})
public class ExampleTestController {
    private static Logger logger = LoggerFactory.getLogger(ExampleTestController.class);

    @Autowired
    private ExampleTestService exampleTestService;
    @Autowired
    private ExampleTestMapper exampleTestMapper;


    @API(summary = "测试接口", description = "单个接口自输入参数调试 ExampleTestController.java:exampleTest",
            parameters = {
            @Param(name = "params",
                    description = "{\"id\":0,\"params\":{接口参数}}")
            }
    )
    @ResponseBody
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ResultDTO exampleTest(HttpServletRequest request){
                                 //@RequestParam(name = "params", required = true)String params){
        Integer id = Integer.valueOf(request.getParameter("id"));
        return exampleTestService.callInterface(id,request.getParameter("params"));
    }

    @ResponseBody
    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public ResultDTO test(HttpServletRequest request){
        //@RequestParam(name = "params", required = true)String params){
        Integer id = Integer.valueOf(request.getParameter("id"));
        return exampleTestService.callInterface(id,request.getParameter("params"));
    }

    @API(summary = "自动化测试接口", description = "根据设定时间进行自动化测试"
    )
    @Scheduled(cron = "0 0 3 * * ?") //每天3点钟自动化测试
    public void pushDataScheduled(){
        logger.info("start test scheduled!");
        exampleTestService.Test();
        logger.info("end test scheduled!");
    }

    @API(summary = "添加测试用例", description = "添加测试用例 ExampleTestController.java:addTestCase",
            parameters = {
            @Param(name = "id",description = "接口ID",type = "Integer",required =true ),
                    @Param(name = "params",description = "参数",type = "String",required = true)
            }
    )
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResultDTO addTestCase(HttpServletRequest request ){
                                 //@RequestParam(name = "params" , required = true) String params){
        Integer id = Integer.valueOf(request.getParameter("id"));
        return exampleTestService.addTestCase(id,request.getParameter("params"));
    }

    @API(summary = "删除测试用例",description = "根据ID删除测试用例 ExampleTestController.java:deleteTestCase",
            parameters = {
            @Param(name = "id" ,description = "测试用例ID",type = "Integer",required = true)
        }
    )
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultDTO deleteTestCase(HttpServletRequest request ,
                                    @PathVariable Integer id){
        return exampleTestService.deleteTestCase(id);
    }

    @API(summary = "更新测试用例", description = "根据测试用例ID更新测试用例参数 ExampleTestController.java:updateTestCase",
            parameters = {
                @Param(name="id",description = "测试用例ID",type = "Integer",required = true),
                    @Param(name="params",description = "测试用例参数",type = "String",required = true)
            }
    )
    @ResponseBody
    @RequestMapping(value = "/" , method = RequestMethod.PUT)
    public ResultDTO updateTestCase(HttpServletRequest request ){
        Integer id = Integer.valueOf(request.getParameter("id"));
        return exampleTestService.updateTestCase(id,request.getParameter("params"));
    }


}

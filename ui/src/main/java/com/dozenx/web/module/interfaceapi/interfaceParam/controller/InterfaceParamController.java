package com.dozenx.web.module.interfaceapi.interfaceParam.controller;

import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.util.CastUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;
import com.dozenx.web.module.interfaceapi.interfaceParam.service.InterfaceParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author TTTzzz
 * @create 2019-11-04 15:54
 **/
@APIs(description = "参数测试接口")
@RestController
@RequestMapping(value = {"/interfaceParam"})
public class InterfaceParamController extends BaseController {
    @Autowired
    private InterfaceParamService paramsService;

    @API(summary = "测试接口")
    @ResponseBody
    @GetMapping(value = {"/test"})
    public String helloTest(HttpServletRequest request, @RequestParam(name = "params", required = true) String params) throws Exception {
        return "hello world!";
    }

    @API(summary = "增加参数接口")
    @ResponseBody
    @PostMapping(value = {"/add"})
    public ResultDTO addParams(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        logger.info("获取请求参数：" + bodyParam);
        String paramName = CastUtil.toString(MapUtils.getString(bodyParam, "paramName"));
        String paramType = CastUtil.toString(MapUtils.getString(bodyParam, "paramType"));
        String paramComment = CastUtil.toString(MapUtils.getString(bodyParam, "paramComment"));
        int interfaceId = CastUtil.toInteger(MapUtils.getString(bodyParam, "interfaceId"));
        Timestamp currenttime = new Timestamp(System.currentTimeMillis());

        String paramIn = MapUtils.getString(bodyParam,"paramIn");
        String paramValue = MapUtils.getStringValue(bodyParam,"paramValue");


        logger.info(String.valueOf(currenttime));
        InterfaceParam params = new InterfaceParam();
        params.setInterfaceId(interfaceId);
        params.setParamName(paramName);
        params.setParamType(paramType);
        params.setCreateTime(currenttime);
        params.setParamComment(paramComment);


        params.setParamIn(paramIn);
        params.setParamValue(paramValue);


        logger.info(String.valueOf(params.getInterfaceId()));
        int result = paramsService.insert(params);
        return this.getResult(params);
    }

    @API(summary = "增加参数接口")
    @ResponseBody
    @PostMapping(value = {"/addList"})
    public int addParamsList(HttpServletRequest request,@RequestBody(required = true) String bodyParam) throws Exception {
        logger.info("获取请求参数：" + bodyParam);
        Map<String,Object> paramList = JsonUtil.toMap(bodyParam);
        String object = MapUtils.getString(paramList, "list");
        List<Object> list = JSONObject.parseArray(object);

        InterfaceParam[] params = new InterfaceParam[list.size()];
        //Params params = new Params();
        List<InterfaceParam> paramsList = new ArrayList();
        //logger.info("" + object);
        for (int i = 0 ;i < list.size(); i++) {
            Map map =  (Map) list.get(i);
            String paramname = CastUtil.toString(MapUtils.getString(map, "paramname"));
            String paramtype = CastUtil.toString(MapUtils.getString(map, "paramtype"));
            String paramcomment = CastUtil.toString(MapUtils.getString(map, "paramcomment"));
            int interfaceid = CastUtil.toInteger(MapUtils.getString(map, "interfaceid"));

            InterfaceParam params1 = new InterfaceParam();
            params1.setInterfaceId(interfaceid);
            params1.setParamName(paramname);
            params1.setParamType(paramtype);
            params1.setCreateTime(new Timestamp(System.currentTimeMillis()));
            params1.setParamComment(paramcomment);
            params[i] = params1;
            //logger.info(String.valueOf(params[i]));
////            logger.info(params[i].getParamName());
////            params[i].setParamType("string");
////            params[i].setParamComment("客户域账号");
////            params[i].setInterfaceId(1);
////            params[i].setCreateTime(new Timestamp(System.currentTimeMillis()));
            paramsList.add(params[i]);
        }
        int result = paramsService.insertParamsList(paramsList);
        return result;


//        Timestamp currenttime = new Timestamp(System.currentTimeMillis());
//        logger.info(String.valueOf(currenttime));
//        Params params = new Params();

//        logger.info(String.valueOf(params.getInterfaceId()));
//
//        int result = paramsService.insert(params);
//        return result == 1 ? "add success" : "add failure";
        //return "hello";
    }

    @API(summary = "修改参数接口")
    @ResponseBody
    @PutMapping(value = {"/update"})
    public ResultDTO updateParams(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        int id = MapUtils.getIntValue(bodyParam, "id");
        String paramname = CastUtil.toString(MapUtils.getString(bodyParam, "paramName"));
        String paramtype = CastUtil.toString(MapUtils.getString(bodyParam, "paramType"));
        String paramcomment = CastUtil.toString(MapUtils.getString(bodyParam, "paramComment"));
        String paramIn = MapUtils.getString(bodyParam,"paramIn");
        String paramValue = MapUtils.getStringValue(bodyParam,"paramValue");
        int interfaceid = CastUtil.toInteger(MapUtils.getIntValue(bodyParam, "interfaceId"));
        Timestamp currenttime = new Timestamp(System.currentTimeMillis());
        logger.info("更新时间："+ currenttime);
        InterfaceParam params = new InterfaceParam();
        params.setId(id);
        params.setInterfaceId(interfaceid);
        params.setParamName(paramname);
        params.setParamType(paramtype);
        params.setUpdateTime(currenttime);
        params.setParamComment(paramcomment);
        params.setParamIn(paramIn);
        params.setParamValue(paramValue);
        int result = paramsService.updateByPrimaryKeySelective(params);
//        return result == 1 ? "update success" : "update failure";
        return this.getResult();
    }



    @API(summary = "修改参数接口")
    @ResponseBody
    @DeleteMapping(value = {"/delete/{id}"})
    public ResultDTO deleteParams(HttpServletRequest request, @PathVariable(name = "id", required = true) Integer id) throws Exception {


        int result = paramsService.deleteByPrimaryKey(id);

        return this.getResult();
    }

    @API(summary = "根据方法名修改参数接口")
    @ResponseBody
    @DeleteMapping(value = {"/deleteParams"})
    public String deleteParamsByInterfaceId(HttpServletRequest request, @RequestParam(name = "params", required = true) String params) throws Exception {
        Map paramsMap = JsonUtil.toJavaBean(params, Map.class);
        int interfaceid = MapUtils.getIntValue(paramsMap, "interfaceid");
        int result = paramsService.deleteByInterfaceId(interfaceid);
        return result >= 1 ? "delete success" : "delete failure";
    }


    @API(summary = "获取参数列表")
    @ResponseBody
    @GetMapping(value = {"/getList"})
    public InterfaceParam getParamsList(HttpServletRequest request, @RequestParam(name = "params", required = true) String params) throws Exception {
        Map paramsMap = JsonUtil.toJavaBean(params, Map.class);
        int id = MapUtils.getIntValue(paramsMap, "id");
        InterfaceParam result = paramsService.selectByPrimaryKey(id);
        return result;
    }

    @API(summary = "查找方法的参数")
    @ResponseBody
    @GetMapping(value = {"/getParams/{id}"})
    public ResultDTO  getApiParamsList(HttpServletRequest request, @PathVariable("id") int interfaceId) throws Exception {

        List<InterfaceParam> result = paramsService.selectByInterfaceId(interfaceId);
        logger.info("查询到的参数：" + result);
        return this.getDataResult(result);
    }
}

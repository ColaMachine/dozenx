package com.dozenx.web.module.interfaceapi.interfaceinfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.location.service.LocationService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.interfaceapi.exampletest.service.ExampleTestService;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceHeader;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;
import com.dozenx.web.module.interfaceapi.interfaceParam.service.InterfaceParamService;
import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.dozenx.web.module.interfaceapi.interfaceinfo.service.InterfaceInfoService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;


@APIs(description = "接口信息")
@CrossOrigin
@Controller
@RequestMapping(value = {"/interfaceInfo"})
public class InterfaceController extends BaseController {

    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private LocationService locationService;

    @API(summary = "新增接口",
            description = "新增接口 InterfaceController.class:insertApi",
            parameters = {
                    @Param(name = "body", description = "{" +
                            "\"id\": 0, \n" +
                            "\"projectName\": \"string\", \n" +
                            "\"moduleName\": \"string\", \n" +
                            "\"url\": \"string\", \n" +
                            "\"httpType\": \"string\", \n" +
                            "\"contentType\": \"string\", \n" +
                            "\"interfaceName\": \"string\", \n" +
                            "\"interfaceId\": \"string\", \n" +
                            "\"interfaceDetail\": \"string\", \n" +
                            "\"imageUrl\": \"string\", \n" +
                            "\"version\": \"string\", \n" +
                            "\"createTime\": \"string\", \n" +
                            "\"historyFlag\": 0, \n" +
                            "\"splicing\": 0, \n" +
                            "}",
                            in = InType.body, dataType = DataType.STRING, required = true)
            })
    @ResponseBody
    @PutMapping(value = {"/"})
    public ResultDTO insertApi(HttpServletRequest httpServletRequest, @RequestBody InterfaceInfo interfaceInfo) {
        String result = "";
        try {
            interfaceInfoService.insertApi(interfaceInfo);
//            if(interfaceInfoService.insertApi(interfaceInfo) > 0) {
//                result = "success";
//            }
//            else result = "failed";
        } catch (Exception e) {
            return ResultUtil.getFailResult(e.getMessage());
        }
        return ResultUtil.getResult();
    }

    @API(summary = "修改接口",
            description = "修改接口InterfaceController.class:updateApi",
            parameters = {
                    @Param(name = "body", description = "{" +
                            "\"id\": 0, \n" +
                            "\"projectName\": \"string\", \n" +
                            "\"moduleName\": \"string\", \n" +
                            "\"url\": \"string\", \n" +
                            "\"httpType\": \"string\", \n" +
                            "\"contentType\": \"string\", \n" +
                            "\"interfaceName\": \"string\", \n" +
                            "\"interfaceId\": \"string\", \n" +
                            "\"interfaceDetail\": \"string\", \n" +
                            "\"imageUrl\": \"string\", \n" +
                            "\"version\": \"string\", \n" +
                            "\"createTime\": \"string\", \n" +
                            "\"historyFlag\": 0, \n" +
                            "\"splicing\": 0, \n" +
                            "}",
                            in = InType.body, dataType = DataType.STRING, required = false)
            })
    @ResponseBody
    @PostMapping(value = {"/"})
    public ResultDTO updateApi(HttpServletRequest httpServletRequest, @RequestBody InterfaceInfo interfaceInfo) {
        String result = "";
        try {
            interfaceInfoService.updateApi(interfaceInfo);
        } catch (Exception e) {
            return ResultUtil.getFailResult(e.getMessage());
        }
        return ResultUtil.getResult();
    }

    @Autowired
    private InterfaceParamService paramsService;
    @Autowired
    private ExampleTestService exampleTestService;

    @API(summary = "删除接口",
            description = "删除接口 InterfaceController.class: deleteApi",
            parameters = {
                    @Param(name = "", description = "/{id}")
            })
    @ResponseBody
    @DeleteMapping(value = {"/{id}"})
    public ResultDTO deleteApi(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        String result = "";
        try {
//            Integer temp_id = MapUtils.getInteger(id, "id");
            //根据接口id查询params表，并删除相关数据
//            if(paramsService.selectByInterfaceId(id).size() > 0) {
            paramsService.deleteByPrimaryKey(id);
//            }
            interfaceInfoService.deleteApi(id);
        } catch (Exception e) {
            return ResultUtil.getFailResult(e.getMessage());
        }
        return ResultUtil.getResult();
    }

    @API(summary = "查询接口1",
            description = "查询接口1根据id查询",
            parameters = {
                    @Param(name = "", description = "/{id}")
            })
    @ResponseBody
    @GetMapping(value = {"/{id}"})
    public ResultDTO getApiById(HttpServletRequest httpServletRequest, /*@RequestParam(name="id", required = true)*/@PathVariable String id) {
        String errMsg = "";
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        try {
            int in = Integer.parseInt(id);
            interfaceInfo = interfaceInfoService.getApiById(in);
        } catch (Exception e) {
            errMsg = e.toString();
        }
        return ResultUtil.getResult(0, interfaceInfo, errMsg);
    }

    @API(summary = "查询接口2",
            description = "查询接口2",
            parameters = {
                    @Param(name = "params", description = "{" +
                            "\"id\": 0, \n" +
                            "\"projectName\": \"string\", \n" +
                            "\"moduleName\": \"string\", \n" +
                            "\"url\": \"string\", \n" +
                            "\"httpType\": \"string\", \n" +
                            "\"contentType\": \"string\", \n" +
                            "\"interfaceName\": \"string\", \n" +
                            "\"interfaceId\": \"string\", \n" +
                            "\"interfaceDetail\": \"string\", \n" +
                            "\"imageUrl\": \"string\", \n" +
                            "\"version\": \"string\", \n" +
                            "\"createTime\": \"string\", \n" +
                            "\"historyFlag\": 0, \n" +
                            "\"splicing\": 0, \n" +
                            "}",
                            in = InType.query, dataType = DataType.STRING, required = true)
            })
    @ResponseBody
    @GetMapping(value = {"/getByParams", "/list"})

    public ResultDTO getApiByParams(HttpServletRequest httpServletRequest, @RequestParam(name = "params", required = true) String params) {
        Map<String, Object> interfaceInfo = JsonUtil.toJavaBean(params, Map.class);
        locationService.getNameByCode("520000");
        Page page = RequestUtil.getPage(interfaceInfo);
        PageInfo<InterfaceInfo> pageInfo = null;
        String errMsg = "";
        try {
            pageInfo = interfaceInfoService.getApiByParams(interfaceInfo, page.getCurPage(), page.getPageSize());
            page.setTotalCount(CastUtil.toInteger(pageInfo.getTotal()));
        } catch (Exception e) {
            errMsg = e.toString();
            return ResultUtil.getFailResult(errMsg);
        }
        return ResultUtil.getResult(0, pageInfo.getList(), errMsg, page);
    }

//===============================================================================================================

    /**
     * @param params
     * @Description:接口信息查询，根据项目名称，接口id，版本 * @param request
     * @return:
     * @Author: 吴勤
     * @date: 2019/11/18
     */
    @API(summary = "接口信息查询，根据项目名称，接口id，版本",
            description = "接口信息查询，根据项目名称，接口id，版本",
            parameters = {
                    @Param(name = "projectName", description = "项目名称", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "interfaceId", description = "接口id", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "version", description = "接口版本", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @ResponseBody
    @GetMapping(value = {"/search"})
    public Map<String, Object> getfInterfaceInfo(HttpServletRequest request, @RequestParam(name = "params", required = true) String params) {

        Map paramsMap = JsonUtil.toJavaBean(params, Map.class);
        logger.info("接口信息查询");
        String projectName = CastUtil.toString(MapUtils.getString(paramsMap, "projectName"));//项目名称
        String interfaceId = CastUtil.toString(MapUtils.getString(paramsMap, "interfaceId"));//设备编码
        String version = CastUtil.toString(MapUtils.getString(paramsMap, "version"));//接口版本
        InterfaceInfo paramsInterface = new InterfaceInfo();
        paramsInterface.setInterfaceId(interfaceId);
        paramsInterface.setProjectName(projectName);
        paramsInterface.setVersion(version);
        List<InterfaceInfo> InterfaceMap = interfaceInfoService.getInterfaceInfo(paramsInterface);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("data", InterfaceMap);
        String errorCode = MapUtils.getString(responseMap, "errorCode");
        String errorMsg = MapUtils.getString(responseMap, "errorMsg");
        return responseMap;
    }

    /**
     * @Description:接口信息新增 * @param request
     * * @param bodyParam
     * @return:
     * @Author: 吴勤
     * @date: 2019/11/21
     */
    @API(summary = "接口信息新增",
            description = "接口信息新增",
            parameters = {
                    @Param(name = "id", description = "主键  ", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "projectName", description = "项目名称", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "interfaceId", description = "接口id", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "version", description = "接口版本", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "moduleName", description = "模块名", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "请求url", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "httpType", description = "请求类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "contenType", description = "参数类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceName", description = "接口名称", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceDetail", description = "接口详情", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "imageUrl", description = "图片路径", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceParams", description = "请求参数", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "historyFlag", description = "是否在用", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "splicing", description = "是否拼接", in = InType.body, dataType = DataType.STRING, required = false)
            })
    @ResponseBody
    @PostMapping(value = {"/add"})
    public ResultDTO add(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {

        logger.info("1.获取接口列表params：" + bodyParam);
        //1.查询接口信息是否存在
        String projectName = CastUtil.toString(MapUtils.getString(bodyParam, "projectName"));//项目名称
        String interfaceId = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceId"));//设备编码
        String version = CastUtil.toString(MapUtils.getString(bodyParam, "version"));//接口版本
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setInterfaceId(interfaceId);
        interfaceInfo.setProjectName(projectName);
        interfaceInfo.setUrl(MapUtils.getString(bodyParam,"url"));
        interfaceInfo.setUrl(MapUtils.getString(bodyParam,"url"));
        interfaceInfo.setVersion(version);
        logger.info("2.校验前端数据");
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        //1.唯一性属性不为空校验
        // interfaceInfo.setInterfaceId(interfaceInfo.getUrl().replaceAll("/","."));
        //   vu.add("interfaceId", interfaceId, "接口ID", new Rule[]{new Length(128), new NotEmpty()});
        vu.add("projectName", projectName, "项目名称", new Rule[]{new Length(255), new NotEmpty()});
        vu.add("version", version, "版本号", new Rule[]{new Length(16), new NotEmpty()});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//为空校验
        }
        //接口根据项目名称，接口名称，版本做唯一性校验,如果同一版本、
        // 同一项目、同一版本、同一接口ID，提示用户对已存在的接口进行修改，减少垃圾数据
//        List<InterfaceInfo> oldInterfaceInfo = interfaceInfoService.getInterfaceInfo(interfaceInfo);
//        if (!oldInterfaceInfo.isEmpty()) {
//            return ResultUtil.getResult(CastUtil.toInteger("001"), projectName + "项目," + version + "版本，接口已经存在,请对已存在的接口进行修改");
//        }
        logger.info("3.插入接口信息");
        InterfaceInfo interfaceInfoNew = packageBean(bodyParam);
        int IresponseMap = interfaceInfoService.insertSelective(interfaceInfoNew);
        Map<String, Object> responseMap = new HashMap<String, Object>();
//        if (IresponseMap == 0) {
//            responseMap.put("errorCode", "0001");
//            responseMap.put("errorMsg", "接口添加失败");
//        } else {
//            responseMap.put("errorCode", "0000");
//            responseMap.put("errorMsg", "接口添加成功");
//        }
        String errorCode = MapUtils.getString(responseMap, "errorCode");
        String errorMsg = MapUtils.getString(responseMap, "errorMsg");
        return ResultUtil.getDataResult(interfaceInfoNew);
    }

    /**
     * @Description:修改接口信息 * @param request
     * * @param bodyParam
     * @return:
     * @Author: 吴勤
     * @date: 2019/11/18
     */
//    Integer historyFlag= CastUtil.toInteger(MapUtils.getString(bodyParam, "historyFlag"));//是否在用
//    Integer id = CastUtil.toInteger(MapUtils.getInteger(bodyParam, "id"));
//        interfaceApi.setId(id);
    @API(summary = "接口信息修改",
            description = "接口信息修改",
            parameters = {
                    @Param(name = "id", description = "主键  ", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "projectName", description = "项目名称", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "interfaceId", description = "接口id", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "version", description = "接口版本", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "moduleName", description = "模块名", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "请求url", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "httpType", description = "请求类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "contenType", description = "参数类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceName", description = "接口名称", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceDetail", description = "接口详情", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "imageUrl", description = "图片路径", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceParams", description = "请求参数", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "historyFlag", description = "是否在用", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "splicing", description = "是否拼接", in = InType.body, dataType = DataType.STRING, required = false)
            })
    @ResponseBody
    @PutMapping(value = {"/update"})
    public ResultDTO update(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String projectName = CastUtil.toString(MapUtils.getString(bodyParam, "projectName"));//项目名称
        String interfaceId = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceId"));//设备编码
        String version = CastUtil.toString(MapUtils.getString(bodyParam, "version"));//接口版本


//        InterfaceInfo paramsInterface = new InterfaceInfo();
//        paramsInterface.setInterfaceId(interfaceId);
//        paramsInterface.setProjectName(projectName);
//        paramsInterface.setVersion(version);
//        List<InterfaceInfo> interfaceMap = interfaceInfoService.getInterfaceInfo(paramsInterface);
//        if (!interfaceMap.isEmpty()) {
//            return ResultUtil.getResult(CastUtil.toInteger("001"), projectName + "项目," + version + "版本，该版本的接口已经存在,请对已存在的接口版本进行修改");
//        }
        //保存之前记录
        Integer id = CastUtil.toInteger(MapUtils.getInteger(bodyParam, "id"));
        InterfaceInfo InterfaceMap = interfaceInfoService.getApiById(id);
        //  List<Map<String, Object>> InterfaceMapParam = interfaceInfoService.selectByInterfaceIdParams(id);
        //设置老记录不用为0，设置id为空，自动生成
        InterfaceMap.setId(null);
        InterfaceMap.setHistoryFlag(1);//不用
        //  InterfaceMap.setInterfaceParams(InterfaceMapParam);
//        interfaceInfoService.insertSelective(InterfaceMap);
        logger.info("1.修改接口信息");
        Map<String, Object> responseMap = new HashMap<String, Object>();
        //接口根据项目名称，接口名称，版本做唯一性校验,如果同一版本、
        // 同一项目、同一版本、同一接口ID，提示用户对已存在的接口进行修改，减少垃圾数据
        //1.查询接口信息是否存在
        InterfaceInfo interfaceApi = packageBean(bodyParam);
        interfaceApi.setCreateTime(InterfaceMap.getCreateTime());
        int resultCount = interfaceInfoService.updateByPrimaryKeySelective(interfaceApi);

//        if (IresponseMap == 0) {
//            responseMap.put("errorCode", "0001");
//            responseMap.put("errorMsg", "接口修改失败");
//
//        } else {
//            responseMap.put("errorCode", "0000");
//            responseMap.put("errorMsg", "接口修改成功");
//        }
//        String errorCode = MapUtils.getString(responseMap, "errorCode");
//        String errorMsg = MapUtils.getString(responseMap, "errorMsg");
        return ResultUtil.getResult();
    }

    /**
     * @Description:封装接口信息 * @param bodyParam
     * 并把参数也塞入的interfaceInfo里
     * @return:
     * @Author: 吴勤
     * @date: 2019/11/18
     */
    public InterfaceInfo packageBean(Map<String, Object> bodyParam) {
        InterfaceInfo interfaceApi = new InterfaceInfo();
        String projectName = CastUtil.toString(MapUtils.getString(bodyParam, "projectName"));//项目名称
        String interfaceId = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceId"));//接口id
        String version = CastUtil.toString(MapUtils.getString(bodyParam, "version"));//接口版本
        String moduleName = CastUtil.toString(MapUtils.getString(bodyParam, "moduleName"));//模块名
        String url = CastUtil.toString(MapUtils.getString(bodyParam, "url"));//url
        String httpType = CastUtil.toString(MapUtils.getString(bodyParam, "httpType"));//请求类型
        String contentType = CastUtil.toString(MapUtils.getString(bodyParam, "contentType"));//参数类型
        String interfaceName = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceName"));//接口名称
        String interfaceDetail = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceDetail"));//接口详情
        String imageUrl = CastUtil.toString(MapUtils.getString(bodyParam, "imageUrl"));//图片路径
        int moduleId = MapUtils.getIntValue(bodyParam,"moduleId");
        interfaceApi.setModuleId(moduleId);
        List<Map<String, Object>> interfaceParams = (List<Map<String, Object>>) bodyParam.get("interfaceParams");
        List<Map<String, String>> headers = (List<Map<String, String>>) bodyParam.get("headers");

        List<InterfaceHeader> headerList =new ArrayList<>();
        for(Map<String, String> map: headers){
            InterfaceHeader header  =new InterfaceHeader();
            header.setKey(map.get("key"));
            header.setValue(map.get("value"));
            headerList.add(header);
        }
        interfaceApi.setInterfaceHeaderList(headerList);
        Integer historyFlag = CastUtil.toInteger(MapUtils.getString(bodyParam, "historyFlag"));//是否在用
        Integer splicing = CastUtil.toInteger(MapUtils.getString(bodyParam, "splicing"));//是否拼接
        String author = MapUtils.getString(bodyParam,"author");
        String domain = CastUtil.toString(MapUtils.getString(bodyParam, "domain"));//接口详情

        interfaceApi.setInterfaceHeaders(MapUtils.getString(bodyParam, "interfaceHeaders"));

        interfaceApi.setInterfaceBody(MapUtils.getString(bodyParam, "interfaceBody"));
        interfaceApi.setDomain(domain);
        interfaceApi.setAuthor(author);
        Integer id = CastUtil.toInteger(MapUtils.getInteger(bodyParam, "id"));
        interfaceApi.setId(id);
        interfaceApi.setProjectName(projectName);
        interfaceApi.setInterfaceId(interfaceId);
        interfaceApi.setModuleName(moduleName);
        interfaceApi.setImageUrl(imageUrl);
        interfaceApi.setVersion(version);
        interfaceApi.setHttpType(httpType);
        interfaceApi.setUrl(url);
        interfaceApi.setInterfaceName(interfaceName);
        interfaceApi.setInterfaceDetail(interfaceDetail);
        interfaceApi.setContentType(contentType);   //content type  application/json  form  url_unformed
        interfaceApi.setHistoryFlag(historyFlag == null ? 0 : historyFlag);  //  bug 有可能为空 空指针异常
        interfaceApi.setSplicing(splicing);
        if (interfaceParams != null) {
            List<InterfaceParam> paramList = new ArrayList<InterfaceParam>(interfaceParams.size());
            for (Map<String, Object> map : interfaceParams) {
                InterfaceParam interfaceParam = new InterfaceParam();
                interfaceParam.setId(MapUtils.getInteger(map, "id"));
                interfaceParam.setInterfaceId(interfaceApi.getId());
                interfaceParam.setParamComment(MapUtils.getString(map, "paramComment")); // 参数备注
                interfaceParam.setParamName(MapUtils.getString(map, "paramName"));   //参数名称
                interfaceParam.setParamIn(MapUtils.getString(map, "paramIn"));   //参数位置
                interfaceParam.setParamType(MapUtils.getString(map, "paramType"));   //参数类型
                interfaceParam.setParamValue(MapUtils.getString(map, "paramValue")); //参数末班之
                paramList.add(interfaceParam);

            }
//            interfaceApi
            interfaceApi.setInterfaceParams(paramList);
        }


        return interfaceApi;
    }

    @API(summary = "接口信息修改",
            description = "接口信息修改",
            parameters = {
                    @Param(name = "id", description = "主键  ", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "projectName", description = "项目名称", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "interfaceId", description = "接口id", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "version", description = "接口版本", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "moduleName", description = "模块名", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "请求url", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "httpType", description = "请求类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "contenType", description = "参数类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceName", description = "接口名称", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceDetail", description = "接口详情", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "imageUrl", description = "图片路径", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "interfaceParams", description = "请求参数", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "historyFlag", description = "是否在用", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "splicing", description = "是否拼接", in = InType.body, dataType = DataType.STRING, required = false)
            })
    @ResponseBody
    @PutMapping(value = {"/test"})
    public ResultDTO test(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        logger.info("1.修改接口信息");
        //接口根据项目名称，接口名称，版本做唯一性校验,如果同一版本、
        // 同一项目、同一版本、同一接口ID，提示用户对已存在的接口进行修改，减少垃圾数据
        //1.查询接口信息是否存在
        InterfaceInfo interfaceInfo = packageBean(bodyParam);

        String url = interfaceInfo.getUrl() + "?1=1";
        String contentType = interfaceInfo.getContentType();
        Map json = new HashMap<String, Object>();

        for (int i = 0; i < interfaceInfo.getInterfaceParams().size(); i++) {
            InterfaceParam interfaceParam = interfaceInfo.getInterfaceParams().get(i);
            json.put(interfaceParam.getParamName(), interfaceParam.getParamValue());
        }
        boolean paramsGetFlag = false, bodyJsonFlag = false;
        Map params = new HashMap();
        List<InterfaceParam> interfaceParamList = interfaceInfo.getInterfaceParams();
        if (interfaceInfo.getInterfaceParams() != null && interfaceInfo.getInterfaceParams().size() > 0 && interfaceInfo.getInterfaceParams().get(0).getParamIn().toLowerCase().equals("body")) {
            bodyJsonFlag = true;
            json = JsonUtil.toMap(interfaceInfo.getInterfaceParams().get(0).getParamValue());
        } else {
            for (int i = 0; i < interfaceParamList.size(); i++) {
                InterfaceParam nowParam = interfaceParamList.get(i);
                String paramName = nowParam.getParamName();
                String paramType = nowParam.getParamType();
                String paramIn = nowParam.getParamIn();
                String paramValue = nowParam.getParamValue();
                if (paramType != null && paramType.toLowerCase().equals("array")) {//如果参数的格式是数组的话
                    if (paramValue != null) {//alert("怎么会有数组的");
                        if (paramValue.indexOf("[") != -1) {
                            //如果有数组参数就转换成字符串json格式
                            json.put(paramName, JsonUtil.toJavaBean(paramValue, ArrayList.class));
                        }
                    }

                }
                if (paramIn.toLowerCase().equals("body")) {
                    throw new BizException("3030120123", "body请求体的参数不能超过1个");
                }
                if (paramIn.toLowerCase().equals("query")) {//如果有url参数 就放到url参数里面
                    //如果是查询参数就拼接到url里
                    url += "&" + paramName + "=" + URLEncoder.encode(paramValue);
                    // delete json[paramName];//如果在query 里的 就删除掉
                    json.remove(paramName);
                }

                if (paramIn.toLowerCase().equals("path")) {//如果有url参数 就放到url参数里面
                    //如果是url path 参数就拼接到url里
                    //  url+="/"+json[this.content.parameters[i].name];
                    if (url.indexOf("{") > 0) {    //如果含有 if contain path variable
                        // console.log("进行替换");
                        String replaceId = url.substring(url.indexOf("{") + 1, url.indexOf("}") );//- url.indexOf("{") - 1
                        url = url.replace("{" + replaceId + "}", (String) json.get(replaceId)); //replace it put the variable into url
                        //alert(replaceId);
                    }
                }
            }
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        URLUtil.connact(interfaceInfo.getDomain(), url);
        MediaType mediaType = MediaType.parse(interfaceInfo.getContentType());

        OkHttpClient mOkHttpClient;

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor)
                .build();

        okhttp3.RequestBody requestBody = null;
        if (!json.isEmpty()) {
            if (contentType.toLowerCase().equals("application/form")) {
                FormBody.Builder builder = new FormBody.Builder();

                Iterator it = json.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    builder.add(key, value);

                }

                requestBody = builder.build();


            } else if (contentType.toLowerCase().equals("application/json")) {

                String requestBodyStr = JsonUtil.toJson(json);
                requestBody = okhttp3.RequestBody.create(mediaType, requestBodyStr);

            }
        }

        if(!interfaceInfo.getDomain().startsWith("http")){
            interfaceInfo.setDomain("http://"+interfaceInfo.getDomain());
        }
        Request.Builder requestBuilder = new Request.Builder().url( URLUtil.connact(interfaceInfo.getDomain(),url));
        requestBuilder.header("authorization","229A36B1C05EA533F53072E04B86068C");
        if (interfaceInfo.getHttpType().toLowerCase().equals("get")) {
            requestBuilder = requestBuilder.get();
        } else if (interfaceInfo.getHttpType().toLowerCase().equals("post")) {
            //String requestBody = JsonUtil.toJson(json);
            requestBuilder = requestBuilder.post(requestBody);
        } else if (interfaceInfo.getHttpType().toLowerCase().equals("put")) {
            // String requestBody = JsonUtil.toJson(json);
            requestBuilder = requestBuilder.put(requestBody);
        } else if (interfaceInfo.getHttpType().toLowerCase().equals("delete")) {
            requestBuilder = requestBuilder.delete();
        }

        final Request okrequest = requestBuilder.build();
        Call call = okHttpClient.newCall(okrequest);
        Response response = call.execute();
        if (response.isSuccessful()) {
            okhttp3.ResponseBody body = response.body();
            if (body != null) {
                // System.out.println(body.string());
                String result = body.string();
                logger.debug(result);
                return this.getResult(result);

            }
        } else {
            //System.err.println(response.message());
            //logger.error(response.message());
            String msg = response.message();
            logger.error("执行结果"+response.toString());
            return this.getResult(30501004,"执行失败"+response.toString(),"请求失败"+response.toString());
        }

//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: " + response.body().string());
//            }
//        });


        return ResultUtil.getResult();
    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            if(message.length()>100000){
                message=message.substring(0,1000);
            }
            logger.info( message);
        }
    }





    @API(summary = "接口信息修改",
            description = "接口信息修改",
            parameters = {
                    @Param(name = "id", description = "主键  ", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "moduleId", description = "模块id", in = InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "moduleName", description = "模块名称", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @ResponseBody
    @PutMapping(value = {"/module/update"})
    public ResultDTO updateApisModule(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String moduleName = MapUtils.getString(bodyParam, "moduleName");//模块名称
        int moduleId = MapUtils.getIntValue(bodyParam, "moduleId");//模块id
        int id = MapUtils.getIntValue(bodyParam, "id");//主键
        //保存之前记录
        if(id<1000000){
            throw new BizException("30504664","非api数据");
        }
        InterfaceInfo interfaceApi = new InterfaceInfo();
        interfaceApi.setId(id%1000000);
        interfaceApi.setModuleId(moduleId);
        interfaceApi.setModuleName(moduleName);
        int resultCount = interfaceInfoService.updateByPrimaryKeySelective(interfaceApi);
        return ResultUtil.getResult();
    }
    //http://127.0.0.1:8081/home/interfaceInfo/sync?url=http://127.0.0.1:8081/home/api
    @GetMapping(value = {"sync"})
    public ResultDTO collectFromSwaggerApi(@RequestParam(value= "url") String url){
        String jsonStr = HttpRequestUtil.sendGet(url);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONObject paths = (JSONObject)jsonObject.get("paths");     //提取paths元素  所有的url为key 内容是 post : get:等协议对应的内容
        Set<Map.Entry<String,Object>> set = paths.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,Object> entry = (Map.Entry<String,Object>)it.next();
            String apiurl  =  entry.getKey();
            JSONObject  postGetvalue =(JSONObject) entry.getValue();

            getInnerValue(apiurl,postGetvalue);

        }
        return this.getResult();
    }


    public void  getInnerValue(String apiUrl,JSONObject  postGetvalue){

        Set<Map.Entry<String,Object>> set = postGetvalue.entrySet();
        Iterator it = set.iterator();

        while(it.hasNext()){
            Map.Entry<String,Object> entry = (Map.Entry<String,Object> )it.next();
            String httpType  =  entry.getKey();     //post get
            JSONObject  apiInfo =(JSONObject) entry.getValue();

            InterfaceInfo interfaceInfo =new InterfaceInfo();
            interfaceInfo.setInterfaceParams(new ArrayList<>());
            interfaceInfo.setProjectName("工程");
            interfaceInfo.setVersion("v1.0.0");
            //interfaceInfo.setModuleName();
            String contentType =((JSONArray)apiInfo.get("consumes")).get(0).toString();
            interfaceInfo.setContentType(contentType);
//            String summary = apiInfo.get("summary").toString();
//            String response =apiInfo.get("response").toString();
            interfaceInfo.setHttpType(httpType);
            String summary = MapUtils.getString(apiInfo,"summary");
            if(StringUtil.isNotBlank(summary)){
                interfaceInfo.setInterfaceName(summary);
            }

            String description = MapUtils.getString(apiInfo,"description");
            if(StringUtil.isNotBlank(description)){
                interfaceInfo.setInterfaceDetail(description);
            }

            interfaceInfo.setUrl(apiUrl);

            JSONArray list  = (JSONArray)apiInfo.get("parameters");

            for(int i =0 ;i<list.size();i++){
                JSONObject jsonObject = (JSONObject)list.get(i);
                InterfaceParam interfaceParam  =toInterfaceParam(jsonObject);   //
                interfaceParam.setInterfaceId(interfaceInfo.getId());
                interfaceInfo.getInterfaceParams().add(interfaceParam);
            }
            interfaceInfoService.insertSelective(interfaceInfo);

        }
    }

    public InterfaceParam toInterfaceParam (JSONObject jsonObject){
        InterfaceParam interfaceParam = new InterfaceParam();
        interfaceParam.setParamComment(MapUtils.getString(jsonObject,"description"));
        logger.info("param_in:"+MapUtils.getString(jsonObject,"in"));
        interfaceParam.setParamIn(MapUtils.getString(jsonObject,"in"));
        if(interfaceParam.getParamIn().length()>8){
            logger.error("参数位置过长:"+interfaceParam.getParamIn());
        }
        interfaceParam.setParamName(MapUtils.getString(jsonObject,"name"));
        String paramType = MapUtils.getString(jsonObject,"type");
        if(StringUtil.isBlank(paramType)){
            logger.error("param type is null");
            paramType ="String";
        }else{

        }


        interfaceParam.setParamType(paramType);

        boolean required = MapUtils.getBoolean(jsonObject,"requried",false);
        interfaceParam.setParamRequired(required);

        return  interfaceParam;
    }



    public static void main(String args[]){
        //构造数据
        try {
            Map<String, String> dataMap = new HashMap<String, String>();
            /*List<User> list = new ArrayList<User>();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setA("a" + (i + 1));
                user.setB("b" + (i + 1));
                user.setC("c" + (i + 1));
                list.add(user);
            }*/
            dataMap.put("httpType", "1");
            dataMap.put("url", "http");
            dataMap.put("detail", "detail");
            dataMap.put("httpType", "123");
            dataMap.put("output", "123");
            dataMap.put("remark", "123");
            dataMap.put("params","123123");
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File("E:/"));
            File outFile = new File("e:\\test.doc");
            Template t = configuration.getTemplate("template.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            t.process(dataMap, out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
package com.dozenx.web.module.interfaceapi.interfaceinfo.controller;

import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.CastUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.location.action.LocationController;
import com.dozenx.web.core.location.service.LocationService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.interfaceapi.exampletest.service.ExampleTestService;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;
import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.dozenx.web.module.interfaceapi.interfaceinfo.service.InterfaceInfoService;
import com.dozenx.web.module.interfaceapi.interfaceParam.service.InterfaceParamService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.github.pagehelper.PageInfo;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @GetMapping(value = {"/getByParams","/list"})

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
        List<InterfaceInfo> oldInterfaceInfo = interfaceInfoService.getInterfaceInfo(interfaceInfo);
        if (!oldInterfaceInfo.isEmpty()) {
            return ResultUtil.getResult(CastUtil.toInteger("001"), projectName + "项目," + version + "版本，接口已经存在,请对已存在的接口进行修改");
        }
        logger.info("3.插入接口信息");
        InterfaceInfo interfaceInfoNew =packageBean(bodyParam);
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
        String moduleName = CastUtil.toString(MapUtils.getString(bodyParam, "modelName"));//模块名
        String url = CastUtil.toString(MapUtils.getString(bodyParam, "url"));//url
        String httpType = CastUtil.toString(MapUtils.getString(bodyParam, "httpType"));//请求类型
        String contentType = CastUtil.toString(MapUtils.getString(bodyParam, "contentType"));//参数类型
        String interfaceName = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceName"));//接口名称
        String interfaceDetail = CastUtil.toString(MapUtils.getString(bodyParam, "interfaceDetail"));//接口详情
        String imageUrl = CastUtil.toString(MapUtils.getString(bodyParam, "imageUrl"));//图片路径
        List<Map<String, Object>> interfaceParams = (List<Map<String, Object>>) bodyParam.get("interfaceParams");
        Integer historyFlag = CastUtil.toInteger(MapUtils.getString(bodyParam, "historyFlag"));//是否在用
        Integer splicing = CastUtil.toInteger(MapUtils.getString(bodyParam, "splicing"));//是否拼接
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
        interfaceApi.setHistoryFlag(historyFlag==null ?0:historyFlag);  //  bug 有可能为空 空指针异常
        interfaceApi.setSplicing(splicing);
        if(interfaceParams!=null){
            List<InterfaceParam> paramList =new ArrayList<InterfaceParam>(interfaceParams.size());
            for(Map<String,Object> map : interfaceParams){
                InterfaceParam interfaceParam =new InterfaceParam();
                interfaceParam.setInterfaceId(interfaceApi.getId());
                interfaceParam.setParamComment(MapUtils.getString(map,"paramComment")); // 参数备注
                interfaceParam.setParamName(MapUtils.getString(map,"paramName"));   //参数名称
                interfaceParam.setParamIn(MapUtils.getString(map,"paramIn"));   //参数位置
                interfaceParam.setParamType(MapUtils.getString(map,"paramType"));   //参数类型
                interfaceParam.setParamValue(MapUtils.getString(map,"paramValue")); //参数末班之
                paramList.add(interfaceParam);

            }
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

        return ResultUtil.getResult();
    }



}
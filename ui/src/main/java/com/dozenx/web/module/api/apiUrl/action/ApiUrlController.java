/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-4-10 15:42:40
 * 文件说明:
 */

package com.dozenx.web.module.api.apiUrl.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.SysConfig;
import com.dozenx.core.exception.BizException;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.module.api.apiUrl.bean.ApiUrl;
import com.dozenx.web.module.api.apiUrl.service.ApiUrlService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

@APIs(description = "api分类")
@Controller
@RequestMapping("/api/url/")
public class ApiUrlController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(ApiUrlController.class);
    /**
     * 权限service
     **/
    @Autowired
    private ApiUrlService apiUrlService;


    /**
     * 说明:ajax请求ApiUrl信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "api分类列表接口",
            description = "api分类列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, schema = "{1:'2',3:'4'}",required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),// false
                    @Param(name = "name", description = "名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "url", description = "url", dataType = DataType.STRING, required = false),// false
                    @Param(name = "pid", description = "父id", dataType = DataType.LONG, required = false),// false
                    @Param(name = "summary", description = "概要", dataType = DataType.STRING, required = false),// false
                    @Param(name = "description", description = "备注", dataType = DataType.STRING, required = false),// false
                    @Param(name = "consumes", description = "允许的请求类型", dataType = DataType.STRING, required = false),// false
                    @Param(name = "produces", description = "响应MIME", dataType = DataType.STRING, required = false),// false
                    @Param(name = "createUser", description = "创建者", dataType = DataType.STRING, required = false),// false
                    @Param(name = "deprecated", description = "是否废弃", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "json", description = "json", dataType = DataType.STRING, required = false),// false
                    @Param(name = "createtime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),// false
                    @Param(name = "updatetime", description = "更新时间", dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {
        Page page = RequestUtil.getPage(request);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            params.put("url", url);
        }
        String urlLike = request.getParameter("urlLike");
        if (!StringUtil.isBlank(urlLike)) {
            params.put("urlLike", urlLike);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            params.put("summary", summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if (!StringUtil.isBlank(summaryLike)) {
            params.put("summaryLike", summaryLike);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            params.put("description", description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if (!StringUtil.isBlank(descriptionLike)) {
            params.put("descriptionLike", descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            params.put("consumes", consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if (!StringUtil.isBlank(consumesLike)) {
            params.put("consumesLike", consumesLike);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            params.put("produces", produces);
        }
        String producesLike = request.getParameter("producesLike");
        if (!StringUtil.isBlank(producesLike)) {
            params.put("producesLike", producesLike);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            params.put("createUser", createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if (!StringUtil.isBlank(createUserLike)) {
            params.put("createUserLike", createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            params.put("deprecated", deprecated);
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            params.put("json", json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<ApiUrl> apiUrls = apiUrlService.listByParams4Page(params);
        return ResultUtil.getResult(apiUrls, page);
    }

    /**
     * 说明:ajax请求ApiUrl信息 无分页版本
     *
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            params.put("url", url);
        }
        String urlLike = request.getParameter("urlLike");
        if (!StringUtil.isBlank(urlLike)) {
            params.put("urlLike", urlLike);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            params.put("summary", summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if (!StringUtil.isBlank(summaryLike)) {
            params.put("summaryLike", summaryLike);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            params.put("description", description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if (!StringUtil.isBlank(descriptionLike)) {
            params.put("descriptionLike", descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            params.put("consumes", consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if (!StringUtil.isBlank(consumesLike)) {
            params.put("consumesLike", consumesLike);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            params.put("produces", produces);
        }
        String producesLike = request.getParameter("producesLike");
        if (!StringUtil.isBlank(producesLike)) {
            params.put("producesLike", producesLike);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            params.put("createUser", createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if (!StringUtil.isBlank(createUserLike)) {
            params.put("createUserLike", createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            params.put("deprecated", deprecated);
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            params.put("json", json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<ApiUrl> apiUrls = apiUrlService.listByParams(params);
        return ResultUtil.getDataResult(apiUrls);
    }

    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "根据id查询单个api分类信息",
            description = "根据id查询单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "id", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap</*String,Object*/>();
        ApiUrl bean = apiUrlService.selectByPrimaryKey(Long.valueOf(id));
        result.put("bean", bean);
        return this.getResult(result);

    }


    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "根据id查询单个api分类信息",
            description = "根据id查询单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
        String id = request.getParameter("id");
        ApiUrl bean = apiUrlService.selectByPrimaryKey(Long.valueOf(id));
        //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        // result.put("bean", bean);
        return this.getResult(bean);
    }


    /**
     * 说明:更新ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "更新id更新单个api分类信息",
            description = "更新id更新单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", type = "LONG", required = false),
                    @Param(name = "name", description = "名称", type = "STRING", required = false),
                    @Param(name = "url", description = "url", type = "STRING", required = false),
                    @Param(name = "pid", description = "父id", type = "LONG", required = false),
                    @Param(name = "summary", description = "概要", type = "STRING", required = false),
                    @Param(name = "description", description = "备注", type = "STRING", required = false),
                    @Param(name = "consumes", description = "允许的请求类型", type = "STRING", required = false),
                    @Param(name = "produces", description = "响应MIME", type = "STRING", required = false),
                    @Param(name = "createUser", description = "创建者", type = "STRING", required = false),
                    @Param(name = "deprecated", description = "是否废弃", type = "INTEGER", required = false),
                    @Param(name = "json", description = "json", type = "STRING", required = false),
                    @Param(name = "createtime", description = "创建时间", type = "DATE_TIME", required = false),
                    @Param(name = "updatetime", description = "更新时间", type = "DATE_TIME", required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json", method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        ApiUrl apiUrl = new ApiUrl();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            apiUrl.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            apiUrl.setName(String.valueOf(name)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            apiUrl.setUrl(String.valueOf(url)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            apiUrl.setPid(Long.valueOf(pid)) ;
        }
        
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            apiUrl.setSummary(String.valueOf(summary)) ;
        }
        
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            apiUrl.setDescription(String.valueOf(description)) ;
        }
        
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            apiUrl.setConsumes(String.valueOf(consumes)) ;
        }
        
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            apiUrl.setProduces(String.valueOf(produces)) ;
        }
        
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            apiUrl.setCreateUser(String.valueOf(createUser)) ;
        }
        
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            apiUrl.setDeprecated(Integer.valueOf(deprecated)) ;
        }
        
        String json = request.getParameter("json");
        if(!StringUtil.isBlank(json)){
            apiUrl.setJson(String.valueOf(json)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            apiUrl.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            apiUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            apiUrl.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            apiUrl.setName(name);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            apiUrl.setUrl(url);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            apiUrl.setSummary(summary);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            apiUrl.setDescription(description);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            apiUrl.setConsumes(consumes);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            apiUrl.setProduces(produces);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            apiUrl.setCreateUser(createUser);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            apiUrl.setJson(json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setCreatetime(new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setUpdatetime(new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("name", name, "名称", new Rule[]{new Length(50)});
        vu.add("url", url, "url", new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id", new Rule[]{new Digits(15, 0)});
        vu.add("summary", summary, "概要", new Rule[]{new Length(200)});
        vu.add("description", description, "备注", new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型", new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME", new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者", new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "0"})});
        vu.add("json", json, "json", new Rule[]{});
        vu.add("createtime", createtime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return apiUrlService.save(apiUrl);

    }


    /**
     * 说明:添加ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API(summary = "添加单个api分类信息",
            description = "添加单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "name", description = "名称", dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "url", dataType = DataType.STRING, required = false),
                    @Param(name = "pid", description = "父id", dataType = DataType.LONG, required = false),
                    @Param(name = "summary", description = "概要", dataType = DataType.STRING, required = false),
                    @Param(name = "description", description = "备注", dataType = DataType.STRING, required = false),
                    @Param(name = "consumes", description = "允许的请求类型", dataType = DataType.STRING, required = false),
                    @Param(name = "produces", description = "响应MIME", dataType = DataType.STRING, required = false),
                    @Param(name = "createUser", description = "创建者", dataType = DataType.STRING, required = false),
                    @Param(name = "deprecated", description = "是否废弃", dataType = DataType.INTEGER, required = false),
                    @Param(name = "json", description = "json", dataType = DataType.STRING, required = false),
                    @Param(name = "createtime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
                    @Param(name = "updatetime", description = "更新时间", dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "add.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        ApiUrl apiUrl = new ApiUrl();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                apiUrl.setId(Long.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                apiUrl.setName(String.valueOf(name)) ;
            }
            
            String url = request.getParameter("url");
            if(!StringUtil.isBlank(url)){
                apiUrl.setUrl(String.valueOf(url)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                apiUrl.setPid(Long.valueOf(pid)) ;
            }
            
            String summary = request.getParameter("summary");
            if(!StringUtil.isBlank(summary)){
                apiUrl.setSummary(String.valueOf(summary)) ;
            }
            
            String description = request.getParameter("description");
            if(!StringUtil.isBlank(description)){
                apiUrl.setDescription(String.valueOf(description)) ;
            }
            
            String consumes = request.getParameter("consumes");
            if(!StringUtil.isBlank(consumes)){
                apiUrl.setConsumes(String.valueOf(consumes)) ;
            }
            
            String produces = request.getParameter("produces");
            if(!StringUtil.isBlank(produces)){
                apiUrl.setProduces(String.valueOf(produces)) ;
            }
            
            String createUser = request.getParameter("createUser");
            if(!StringUtil.isBlank(createUser)){
                apiUrl.setCreateUser(String.valueOf(createUser)) ;
            }
            
            String deprecated = request.getParameter("deprecated");
            if(!StringUtil.isBlank(deprecated)){
                apiUrl.setDeprecated(Integer.valueOf(deprecated)) ;
            }
            
            String json = request.getParameter("json");
            if(!StringUtil.isBlank(json)){
                apiUrl.setJson(String.valueOf(json)) ;
            }
            
            String createtime = request.getParameter("createtime");
            if(!StringUtil.isBlank(createtime)){
                apiUrl.setCreatetime(Timestamp.valueOf(createtime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            apiUrl.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            apiUrl.setName(name);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            apiUrl.setUrl(url);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            apiUrl.setSummary(summary);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            apiUrl.setDescription(description);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            apiUrl.setConsumes(consumes);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            apiUrl.setProduces(produces);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            apiUrl.setCreateUser(createUser);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            apiUrl.setJson(json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setCreatetime(new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setUpdatetime(new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("name", name, "名称", new Rule[]{new Length(50)});
        vu.add("url", url, "url", new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id", new Rule[]{new Digits(15, 0)});
        vu.add("summary", summary, "概要", new Rule[]{new Length(200)});
        vu.add("description", description, "备注", new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型", new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME", new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者", new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "0"})});
        vu.add("json", json, "json", new Rule[]{});
        vu.add("createtime", createtime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return apiUrlService.save(apiUrl);

    }


    /**
     * 说明:添加ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个api分类信息",
            description = "添加单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "name", description = "名称", dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "url", dataType = DataType.STRING, required = false),
                    @Param(name = "pid", description = "父id", dataType = DataType.LONG, required = false),
                    @Param(name = "summary", description = "概要", dataType = DataType.STRING, required = false),
                    @Param(name = "description", description = "备注", dataType = DataType.STRING, required = false),
                    @Param(name = "consumes", description = "允许的请求类型", dataType = DataType.STRING, required = false),
                    @Param(name = "produces", description = "响应MIME", dataType = DataType.STRING, required = false),
                    @Param(name = "createUser", description = "创建者", dataType = DataType.STRING, required = false),
                    @Param(name = "deprecated", description = "是否废弃", dataType = DataType.INTEGER, required = false),
                    @Param(name = "json", description = "json", dataType = DataType.STRING, required = false),
                    @Param(name = "createtime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
                    @Param(name = "updatetime", description = "更新时间", dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        ApiUrl apiUrl = new ApiUrl();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        apiUrl.setId(Long.valueOf(id)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        apiUrl.setName(String.valueOf(name)) ;
                    }
                    
                    String url = request.getParameter("url");
                    if(!StringUtil.isBlank(url)){
                        apiUrl.setUrl(String.valueOf(url)) ;
                    }
                    
                    String pid = request.getParameter("pid");
                    if(!StringUtil.isBlank(pid)){
                        apiUrl.setPid(Long.valueOf(pid)) ;
                    }
                    
                    String summary = request.getParameter("summary");
                    if(!StringUtil.isBlank(summary)){
                        apiUrl.setSummary(String.valueOf(summary)) ;
                    }
                    
                    String description = request.getParameter("description");
                    if(!StringUtil.isBlank(description)){
                        apiUrl.setDescription(String.valueOf(description)) ;
                    }
                    
                    String consumes = request.getParameter("consumes");
                    if(!StringUtil.isBlank(consumes)){
                        apiUrl.setConsumes(String.valueOf(consumes)) ;
                    }
                    
                    String produces = request.getParameter("produces");
                    if(!StringUtil.isBlank(produces)){
                        apiUrl.setProduces(String.valueOf(produces)) ;
                    }
                    
                    String createUser = request.getParameter("createUser");
                    if(!StringUtil.isBlank(createUser)){
                        apiUrl.setCreateUser(String.valueOf(createUser)) ;
                    }
                    
                    String deprecated = request.getParameter("deprecated");
                    if(!StringUtil.isBlank(deprecated)){
                        apiUrl.setDeprecated(Integer.valueOf(deprecated)) ;
                    }
                    
                    String json = request.getParameter("json");
                    if(!StringUtil.isBlank(json)){
                        apiUrl.setJson(String.valueOf(json)) ;
                    }
                    
                    String createtime = request.getParameter("createtime");
                    if(!StringUtil.isBlank(createtime)){
                        apiUrl.setCreatetime(Timestamp.valueOf(createtime)) ;
                    }
                    
                    String updatetime = request.getParameter("updatetime");
                    if(!StringUtil.isBlank(updatetime)){
                        apiUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
                    }
                    */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            apiUrl.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            apiUrl.setName(name);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            apiUrl.setUrl(url);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            apiUrl.setSummary(summary);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            apiUrl.setDescription(description);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            apiUrl.setConsumes(consumes);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            apiUrl.setProduces(produces);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            apiUrl.setCreateUser(createUser);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            apiUrl.setJson(json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setCreatetime(new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setUpdatetime(new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("name", name, "名称", new Rule[]{new Length(50)});
        vu.add("url", url, "url", new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id", new Rule[]{new Digits(15, 0)});
        vu.add("summary", summary, "概要", new Rule[]{new Length(200)});
        vu.add("description", description, "备注", new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型", new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME", new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者", new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "0"})});
        vu.add("json", json, "json", new Rule[]{});
        vu.add("createtime", createtime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return apiUrlService.save(apiUrl);

    }

    /**
     * 说明:删除ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "根据id删除单个api分类信息",
            description = "根据id删除单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete.json", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
        String idStr = request.getParameter("id");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        apiUrlService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 说明:删除ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "根据id删除单个api分类信息",
            description = "根据id删除单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id, HttpServletRequest request) {
        apiUrlService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 多行删除
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public ResultDTO multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[] = idStr.split(",");
        Long idAry[] = new Long[idStrAry.length];
        for (int i = 0, length = idAry.length; i < length; i++) {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            String id = idStrAry[i];
            vu.add("id", id, "编号", new Rule[]{});

            try {
                validStr = vu.validateString();
            } catch (Exception e) {
                e.printStackTrace();
                validStr = "验证程序异常";
                return ResultUtil.getResult(302, validStr);
            }

            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }
            idAry[i] = Long.valueOf(idStrAry[i]);
        }
        return apiUrlService.multilDelete(idAry);
    }

    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public ResultDTO exportExcel(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String url = request.getParameter("url");
        if (!StringUtil.isBlank(url)) {
            params.put("url", url);
        }
        String urlLike = request.getParameter("urlLike");
        if (!StringUtil.isBlank(urlLike)) {
            params.put("urlLike", urlLike);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }
        String summary = request.getParameter("summary");
        if (!StringUtil.isBlank(summary)) {
            params.put("summary", summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if (!StringUtil.isBlank(summaryLike)) {
            params.put("summaryLike", summaryLike);
        }
        String description = request.getParameter("description");
        if (!StringUtil.isBlank(description)) {
            params.put("description", description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if (!StringUtil.isBlank(descriptionLike)) {
            params.put("descriptionLike", descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if (!StringUtil.isBlank(consumes)) {
            params.put("consumes", consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if (!StringUtil.isBlank(consumesLike)) {
            params.put("consumesLike", consumesLike);
        }
        String produces = request.getParameter("produces");
        if (!StringUtil.isBlank(produces)) {
            params.put("produces", produces);
        }
        String producesLike = request.getParameter("producesLike");
        if (!StringUtil.isBlank(producesLike)) {
            params.put("producesLike", producesLike);
        }
        String createUser = request.getParameter("createUser");
        if (!StringUtil.isBlank(createUser)) {
            params.put("createUser", createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if (!StringUtil.isBlank(createUserLike)) {
            params.put("createUserLike", createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            params.put("deprecated", deprecated);
        }
        String json = request.getParameter("json");
        if (!StringUtil.isBlank(json)) {
            params.put("json", json);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<ApiUrl> list = apiUrlService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("name", "名称");
        colTitle.put("url", "url");
        colTitle.put("pid", "父id");
        colTitle.put("summary", "概要");
        colTitle.put("description", "备注");
        colTitle.put("consumes", "允许的请求类型");
        colTitle.put("produces", "响应MIME");
        colTitle.put("createUser", "创建者");
        colTitle.put("deprecated", "是否废弃");
        colTitle.put("json", "json");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            ApiUrl sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("name", list.get(i).getName());
            map.put("url", list.get(i).getUrl());
            map.put("pid", list.get(i).getPid());
            map.put("summary", list.get(i).getSummary());
            map.put("description", list.get(i).getDescription());
            map.put("consumes", list.get(i).getConsumes());
            map.put("produces", list.get(i).getProduces());
            map.put("createUser", list.get(i).getCreateUser());
            map.put("deprecated", list.get(i).getDeprecated());
            map.put("json", list.get(i).getJson());
            map.put("createtime", list.get(i).getCreatetime());
            map.put("updatetime", list.get(i).getUpdatetime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, fileName, "导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");

    }

    @API(summary = "批量导入信息",
            description = "批量导入信息",
            consumes = "multipart/form-data",
            parameters = {
                    @Param(name = "file", description = "编号", in = InType.formData, dataType = DataType.FILE, required = true),
            })
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO importExcel(@RequestParam("file") MultipartFile file) {
        // 将spring 的file 装成 普通file
        File xlsFile = null;
        if (file != null) {
            try {
                String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
                String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
                Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
                xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
            } catch (Exception e) {
                logger.error("文件上传出错", e);
                throw new BizException("E041412312", "文件上传出错");
            }
        }
        String result = "";
        try {

            // 将导入的中文列名匹配至数据库对应字段
            int success = 0;
            int fail = 0;
            StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
            //            Map<String, String> colMatch = new HashMap<String, String>();
            //            colMatch.put("姓名", "name");
            //            colMatch.put("单位", "org");
            //            colMatch.put("邮箱", "email");


            List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
            for (int i = 0; i < list.size(); i++) {

                Map<String, String> map = list.get(i);
                String email = MapUtils.getStringValue(map, "邮箱");
                // 检验手机号是否符合规范,不符合continue
                if (!StringUtil.isEmail(email)) {
                    //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
                    logger.info(" import conf ==> the telphone:" + email + " is not email");
                    fail++;
                    errorMsg.append("" + email + " 不是邮箱地址;");
                    continue;
                }
                HashMap params = new HashMap();
                params.put("email", email);
                //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在

                ApiUrl bean = getInfoFromMap(params);

                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    apiUrlService.save(bean);
                    success++;//成功数增加
                } catch (Exception e) {

                    fail++;//失败数增加
                    logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                    errorMsg.append("the telphone:" + email + " update fail;");
                }

            }
            if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
            }
            return this.getResult(3090182, "导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入失败", e);
            if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                throw new BizException("E041412313", "导入的excel需为2003版本");
            } else {
                throw new BizException("E041412313", e.getMessage());
            }
        }


    }


    /**
     * 说明: 跳转到ApiUrl列表页面
     *
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/ApiUrlList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/ApiUrlListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
    public String editHtml(HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/ApiUrlEdit.html";
    }

    /**
     * 说明:跳转查看页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
    public String viewHtml(HttpServletRequest request) {
        return "/static/html/ApiUrlView.html";
    }


    private ApiUrl getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        ApiUrl apiUrl = new ApiUrl();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            apiUrl.setId(Long.valueOf(id));
        }
        String name = MapUtils.getString(bodyParam, "name");
        if (!StringUtil.isBlank(name)) {
            apiUrl.setName(String.valueOf(name));
        }
        String url = MapUtils.getString(bodyParam, "url");
        if (!StringUtil.isBlank(url)) {
            apiUrl.setUrl(String.valueOf(url));
        }
        String pid = MapUtils.getString(bodyParam, "pid");
        if (!StringUtil.isBlank(pid)) {
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = MapUtils.getString(bodyParam, "summary");
        if (!StringUtil.isBlank(summary)) {
            apiUrl.setSummary(String.valueOf(summary));
        }
        String description = MapUtils.getString(bodyParam, "description");
        if (!StringUtil.isBlank(description)) {
            apiUrl.setDescription(String.valueOf(description));
        }
        String consumes = MapUtils.getString(bodyParam, "consumes");
        if (!StringUtil.isBlank(consumes)) {
            apiUrl.setConsumes(String.valueOf(consumes));
        }
        String produces = MapUtils.getString(bodyParam, "produces");
        if (!StringUtil.isBlank(produces)) {
            apiUrl.setProduces(String.valueOf(produces));
        }
        String createUser = MapUtils.getString(bodyParam, "createUser");
        if (!StringUtil.isBlank(createUser)) {
            apiUrl.setCreateUser(String.valueOf(createUser));
        }
        String deprecated = MapUtils.getString(bodyParam, "deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String json = MapUtils.getString(bodyParam, "json");
        if (!StringUtil.isBlank(json)) {
            apiUrl.setJson(String.valueOf(json));
        }
        String createtime = MapUtils.getString(bodyParam, "createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setCreatetime(new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(bodyParam, "updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                apiUrl.setUpdatetime(new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("name", name, "名称", new Rule[]{new Length(50)});
        vu.add("url", url, "url", new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id", new Rule[]{new Digits(15, 0)});
        vu.add("summary", summary, "概要", new Rule[]{new Length(200)});
        vu.add("description", description, "备注", new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型", new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME", new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者", new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "0"})});
        vu.add("json", json, "json", new Rule[]{});
        vu.add("createtime", createtime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return apiUrl;
    }


    /**
     * 说明:添加ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个api分类信息",
            description = "添加单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "name", description = "名称", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "url", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "pid", description = "父id", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "summary", description = "概要", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "description", description = "备注", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "consumes", description = "允许的请求类型", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "produces", description = "响应MIME", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "createUser", description = "创建者", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "deprecated", description = "是否废弃", in = InType.body, dataType = DataType.INTEGER, required = false),
                    @Param(name = "json", description = "json", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "createtime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
                    @Param(name = "updatetime", description = "更新时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        ApiUrl apiUrl = getInfoFromMap(bodyParam);


        return apiUrlService.save(apiUrl);

    }


    /**
     * 说明:添加ApiUrl信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "更新单个api分类信息",
            description = "更新单个api分类信息",
            parameters = {
                    @Param(name = "id", description = "编号  ", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "name", description = "名称  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "url", description = "url  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "pid", description = "父id  ", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "summary", description = "概要  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "description", description = "备注  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "consumes", description = "允许的请求类型  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "produces", description = "响应MIME  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "createUser", description = "创建者  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "deprecated", description = "是否废弃  ", in = InType.body, dataType = DataType.INTEGER, required = false),
                    @Param(name = "json", description = "json  ", in = InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "createtime", description = "创建时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
                    @Param(name = "updatetime", description = "更新时间  ", in = InType.body, dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        ApiUrl apiUrl = getInfoFromMap(bodyParam);
        return apiUrlService.save(apiUrl);

    }

    /**
     * 说明:ajax请求ApiUrl信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2019-4-10 15:42:40
     */
    @API(summary = "api分类列表接口",
            description = "api分类列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号  ", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "name", description = "名称  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "url", description = "url  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "pid", description = "父id  ", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "summary", description = "概要  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "description", description = "备注  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "consumes", description = "允许的请求类型  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "produces", description = "响应MIME  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "createUser", description = "创建者  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "deprecated", description = "是否废弃  ", in = InType.params, dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "json", description = "json  ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "createtime", description = "创建时间  ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
                    @Param(name = "updatetime", description = "更新时间  ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) throws Exception {

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
        Page page = RequestUtil.getPage(params);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        String id = MapUtils.getString(params, "id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = MapUtils.getString(params, "name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = MapUtils.getString(params, "nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String url = MapUtils.getString(params, "url");
        if (!StringUtil.isBlank(url)) {
            params.put("url", url);
        }
        String urlLike = MapUtils.getString(params, "urlLike");
        if (!StringUtil.isBlank(urlLike)) {
            params.put("urlLike", urlLike);
        }
        String pid = MapUtils.getString(params, "pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }
        String summary = MapUtils.getString(params, "summary");
        if (!StringUtil.isBlank(summary)) {
            params.put("summary", summary);
        }
        String summaryLike = MapUtils.getString(params, "summaryLike");
        if (!StringUtil.isBlank(summaryLike)) {
            params.put("summaryLike", summaryLike);
        }
        String description = MapUtils.getString(params, "description");
        if (!StringUtil.isBlank(description)) {
            params.put("description", description);
        }
        String descriptionLike = MapUtils.getString(params, "descriptionLike");
        if (!StringUtil.isBlank(descriptionLike)) {
            params.put("descriptionLike", descriptionLike);
        }
        String consumes = MapUtils.getString(params, "consumes");
        if (!StringUtil.isBlank(consumes)) {
            params.put("consumes", consumes);
        }
        String consumesLike = MapUtils.getString(params, "consumesLike");
        if (!StringUtil.isBlank(consumesLike)) {
            params.put("consumesLike", consumesLike);
        }
        String produces = MapUtils.getString(params, "produces");
        if (!StringUtil.isBlank(produces)) {
            params.put("produces", produces);
        }
        String producesLike = MapUtils.getString(params, "producesLike");
        if (!StringUtil.isBlank(producesLike)) {
            params.put("producesLike", producesLike);
        }
        String createUser = MapUtils.getString(params, "createUser");
        if (!StringUtil.isBlank(createUser)) {
            params.put("createUser", createUser);
        }
        String createUserLike = MapUtils.getString(params, "createUserLike");
        if (!StringUtil.isBlank(createUserLike)) {
            params.put("createUserLike", createUserLike);
        }
        String deprecated = MapUtils.getString(params, "deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            params.put("deprecated", deprecated);
        }
        String json = MapUtils.getString(params, "json");
        if (!StringUtil.isBlank(json)) {
            params.put("json", json);
        }
        String createtime = MapUtils.getString(params, "createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = MapUtils.getString(params, "createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = MapUtils.getString(params, "createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(params, "updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = MapUtils.getString(params, "updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = MapUtils.getString(params, "updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<ApiUrl> apiUrls = apiUrlService.listByParams4Page(params);
        return ResultUtil.getResult(apiUrls, page);
    }


    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary = "api分类列表导出接口",
            description = "api分类列表导出接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号 ", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "name", description = "名称 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "url", description = "url ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "pid", description = "父id ", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "summary", description = "概要 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "description", description = "备注 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "consumes", description = "允许的请求类型 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "produces", description = "响应MIME ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "createUser", description = "创建者 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "deprecated", description = "是否废弃 ", in = InType.params, dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "json", description = "json ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "createtime", description = "创建时间 ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
                    @Param(name = "updatetime", description = "更新时间 ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO exportExcelInBody(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) throws Exception {

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
        Page page = RequestUtil.getPage(params);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        String id = MapUtils.getString(params, "id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String name = MapUtils.getString(params, "name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = MapUtils.getString(params, "nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String url = MapUtils.getString(params, "url");
        if (!StringUtil.isBlank(url)) {
            params.put("url", url);
        }
        String urlLike = MapUtils.getString(params, "urlLike");
        if (!StringUtil.isBlank(urlLike)) {
            params.put("urlLike", urlLike);
        }
        String pid = MapUtils.getString(params, "pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }
        String summary = MapUtils.getString(params, "summary");
        if (!StringUtil.isBlank(summary)) {
            params.put("summary", summary);
        }
        String summaryLike = MapUtils.getString(params, "summaryLike");
        if (!StringUtil.isBlank(summaryLike)) {
            params.put("summaryLike", summaryLike);
        }
        String description = MapUtils.getString(params, "description");
        if (!StringUtil.isBlank(description)) {
            params.put("description", description);
        }
        String descriptionLike = MapUtils.getString(params, "descriptionLike");
        if (!StringUtil.isBlank(descriptionLike)) {
            params.put("descriptionLike", descriptionLike);
        }
        String consumes = MapUtils.getString(params, "consumes");
        if (!StringUtil.isBlank(consumes)) {
            params.put("consumes", consumes);
        }
        String consumesLike = MapUtils.getString(params, "consumesLike");
        if (!StringUtil.isBlank(consumesLike)) {
            params.put("consumesLike", consumesLike);
        }
        String produces = MapUtils.getString(params, "produces");
        if (!StringUtil.isBlank(produces)) {
            params.put("produces", produces);
        }
        String producesLike = MapUtils.getString(params, "producesLike");
        if (!StringUtil.isBlank(producesLike)) {
            params.put("producesLike", producesLike);
        }
        String createUser = MapUtils.getString(params, "createUser");
        if (!StringUtil.isBlank(createUser)) {
            params.put("createUser", createUser);
        }
        String createUserLike = MapUtils.getString(params, "createUserLike");
        if (!StringUtil.isBlank(createUserLike)) {
            params.put("createUserLike", createUserLike);
        }
        String deprecated = MapUtils.getString(params, "deprecated");
        if (!StringUtil.isBlank(deprecated)) {
            params.put("deprecated", deprecated);
        }
        String json = MapUtils.getString(params, "json");
        if (!StringUtil.isBlank(json)) {
            params.put("json", json);
        }
        String createtime = MapUtils.getString(params, "createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = MapUtils.getString(params, "createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = MapUtils.getString(params, "createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = MapUtils.getString(params, "updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = MapUtils.getString(params, "updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = MapUtils.getString(params, "updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<ApiUrl> list = apiUrlService.listByParams4Page(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS") + ".xlsx";

        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";


        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + randomName;
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("name", "名称");
        colTitle.put("url", "url");
        colTitle.put("pid", "父id");
        colTitle.put("summary", "概要");
        colTitle.put("description", "备注");
        colTitle.put("consumes", "允许的请求类型");
        colTitle.put("produces", "响应MIME");
        colTitle.put("createUser", "创建者");
        colTitle.put("deprecated", "是否废弃");
        colTitle.put("json", "json");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            ApiUrl sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("name", list.get(i).getName());
            map.put("url", list.get(i).getUrl());
            map.put("pid", list.get(i).getPid());
            map.put("summary", list.get(i).getSummary());
            map.put("description", list.get(i).getDescription());
            map.put("consumes", list.get(i).getConsumes());
            map.put("produces", list.get(i).getProduces());
            map.put("createUser", list.get(i).getCreateUser());
            map.put("deprecated", list.get(i).getDeprecated());
            map.put("json", list.get(i).getJson());
            map.put("createtime", list.get(i).getCreatetime());
            map.put("updatetime", list.get(i).getUpdatetime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, SysConfig.PATH + "/xlstmp/" + randomName, "导出成功");
            }
                /*
                 * return new ResponseEntity<byte[]>(
                 * FileUtils.readFileToByteArray(new File(fileName)), headers,
                 * HttpStatus.CREATED);
                 */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");

    }

    @RequestMapping(value = "db/save", method = RequestMethod.POST)
    public void saveApiInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        //吧json 数据 传入到对应的名称的数据文档里

        //URL ur1l = com.cpj.swagger.support.internal.templates.FreemarkerUtils.class.getClassLoader().getResource("/com/cpj/swagger/support/internal/templates/ftlh/api.ftlh");

        HashMap<String, String> params = new HashMap<>();


        String s = JsonUtil.toJsonString(bodyParam);
        System.out.println(s);
        String url = MapUtils.getString(bodyParam, "url");
        String summary = MapUtils.getString(bodyParam, "summary");
        String description = MapUtils.getString(bodyParam, "description");
        String httpType = MapUtils.getString(bodyParam, "httpType");
        ApiUrl apiUrl = new ApiUrl();
        apiUrl.setSummary(summary);
        apiUrl.setDescription(description);
        apiUrl.setUrl(url);
        apiUrl.setJson(s);
        apiUrl.setMethod(httpType);
        apiUrl.setResponse(MapUtils.getString(bodyParam, "response"));
       // apiUrl.setConsumes();
        params.put("url", url);
        params.put("method", httpType);
        List<ApiUrl> apiUrls = apiUrlService.listByParams(params);
        if (apiUrls != null && apiUrls.size() > 0) {
            apiUrl.setId(apiUrls.get(0).getId());
        }
        apiUrlService.save(apiUrl);
    }
}

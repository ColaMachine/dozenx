/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-11-19 9:41:34
 * 文件说明:
 */

package com.dozenx.web.module.checkin.faceInfo.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cpj.swagger.annotation.*;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.Config;
import com.dozenx.core.config.SysConfig;
import com.dozenx.core.exception.BizException;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.*;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.checkin.checkinOut.bean.CheckinOut;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTask;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTaskData;
import com.dozenx.web.module.checkin.checkinOut.service.CheckinOutService;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.bean.FaceInfo;
import com.dozenx.web.module.checkin.faceInfo.service.FaceInfoService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualDoorService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualWeixinService;
import com.dozenx.web.util.*;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

@APIs(description = "人脸信息")
@Controller
@RequestMapping("/checkin/faceinfo")
public class FaceInfoController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(FaceInfoController.class);
    /**
     * 权限service
     **/


    @Autowired
    private FaceInfoService faceInfoService;
    @Autowired
    private CheckinOutService checkinOutService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 说明:ajax请求FaceInfo信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "人脸信息列表接口",
            description = "人脸信息列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = false),// true
                    @Param(name = "face", description = "人脸特征数组", dataType = DataType.STRING, required = false),// true
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
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }

        params.put("page", page);
        List<FaceInfo> faceInfos = faceInfoService.listByParams4Page(params);
        return ResultUtil.getResult(faceInfos, page);
    }

    /**
     * 说明:ajax请求FaceInfo信息 无分页版本
     *
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }

        List<FaceInfo> faceInfos = faceInfoService.listByParams(params);
        return ResultUtil.getDataResult(faceInfos);
    }

    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "根据id查询单个人脸信息信息",
            description = "根据id查询单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "id", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap</*String,Object*/>();
        if (id > 0) {
            FaceInfo bean = faceInfoService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

    }


    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "根据id查询单个人脸信息信息",
            description = "根据id查询单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
        String id = request.getParameter("id");
        FaceInfo bean = faceInfoService.selectByPrimaryKey(Long.valueOf(id));
        //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        // result.put("bean", bean);
        return this.getResult(bean);
    }


    /**
     * 说明:更新FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "更新id更新单个人脸信息信息",
            description = "更新id更新单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", type = "LONG", required = false),
                    @Param(name = "userId", description = "用户Id", type = "LONG", required = true),
                    @Param(name = "face", description = "人脸特征数组", type = "STRING", required = true),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json", method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        FaceInfo faceInfo = new FaceInfo();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            faceInfo.setId(Long.valueOf(id)) ;
        }
        
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            faceInfo.setUserId(Long.valueOf(userId)) ;
        }
        
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            faceInfo.setFace(String.valueOf(face)) ;
        }
        */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            faceInfo.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            faceInfo.setUserId(Long.valueOf(userId));
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            faceInfo.setFace(face);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
        vu.add("face", face, "人脸特征数组", new Rule[]{new Length(1000), new NotEmpty()});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return faceInfoService.save(faceInfo);

    }


    /**
     * 说明:添加FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API(summary = "添加单个人脸信息信息",
            description = "添加单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
                    @Param(name = "face", description = "人脸特征数组", dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "add.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        FaceInfo faceInfo = new FaceInfo();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                faceInfo.setId(Long.valueOf(id)) ;
            }
            
            String userId = request.getParameter("userId");
            if(!StringUtil.isBlank(userId)){
                faceInfo.setUserId(Long.valueOf(userId)) ;
            }
            
            String face = request.getParameter("face");
            if(!StringUtil.isBlank(face)){
                faceInfo.setFace(String.valueOf(face)) ;
            }
            */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            faceInfo.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            faceInfo.setUserId(Long.valueOf(userId));
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            faceInfo.setFace(face);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
        vu.add("face", face, "人脸特征数组", new Rule[]{new Length(1000), new NotEmpty()});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return faceInfoService.save(faceInfo);

    }


    /**
     * 说明:添加FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个人脸信息信息",
            description = "添加单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
                    @Param(name = "face", description = "人脸特征数组", dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        FaceInfo faceInfo = new FaceInfo();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        faceInfo.setId(Long.valueOf(id)) ;
                    }
                    
                    String userId = request.getParameter("userId");
                    if(!StringUtil.isBlank(userId)){
                        faceInfo.setUserId(Long.valueOf(userId)) ;
                    }
                    
                    String face = request.getParameter("face");
                    if(!StringUtil.isBlank(face)){
                        faceInfo.setFace(String.valueOf(face)) ;
                    }
                    */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            faceInfo.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            faceInfo.setUserId(Long.valueOf(userId));
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            faceInfo.setFace(face);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
        vu.add("face", face, "人脸特征数组", new Rule[]{new Length(1000), new NotEmpty()});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return faceInfoService.save(faceInfo);

    }

    /**
     * 说明:删除FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "根据id删除单个人脸信息信息",
            description = "根据id删除单个人脸信息信息",
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
        faceInfoService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 说明:删除FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "根据id删除单个人脸信息信息",
            description = "根据id删除单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id, HttpServletRequest request) {
        faceInfoService.delete(id);
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
        return faceInfoService.multilDelete(idAry);
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
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }

        // 查询list集合
        List<FaceInfo> list = faceInfoService.listByParams(params);
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
        colTitle.put("userId", "用户Id");
        colTitle.put("face", "人脸特征数组");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            FaceInfo sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("userId", list.get(i).getUserId());
            map.put("face", list.get(i).getFace());
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
                    @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
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

                FaceInfo bean = getInfoFromMap(params);

                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    faceInfoService.save(bean);
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
     * 说明: 跳转到FaceInfo列表页面
     *
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/FaceInfoList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/FaceInfoListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
    public String editHtml(HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/FaceInfoEdit.html";
    }

    /**
     * 说明:跳转查看页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
    public String viewHtml(HttpServletRequest request) {
        return "/static/html/FaceInfoView.html";
    }


    private FaceInfo getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        FaceInfo faceInfo = new FaceInfo();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            faceInfo.setId(Long.valueOf(id));
        }
        String userId = MapUtils.getString(bodyParam, "userId");
        if (!StringUtil.isBlank(userId)) {
            faceInfo.setUserId(Long.valueOf(userId));
        }
        String face = MapUtils.getString(bodyParam, "face");
        if (!StringUtil.isBlank(face)) {
            faceInfo.setFace(String.valueOf(face));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
        vu.add("face", face, "人脸特征数组", new Rule[]{new Length(1000), new NotEmpty()});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return faceInfo;
    }


    /**
     * 说明:添加FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个人脸信息信息",
            description = "添加单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "face", description = "人脸特征数组", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        FaceInfo faceInfo = getInfoFromMap(bodyParam);


        return faceInfoService.save(faceInfo);

    }


    /**
     * 说明:添加FaceInfo信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "更新单个人脸信息信息",
            description = "更新单个人脸信息信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "face", description = "人脸特征数组", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        FaceInfo faceInfo = getInfoFromMap(bodyParam);
        return faceInfoService.save(faceInfo);

    }

    /**
     * 说明:ajax请求FaceInfo信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-11-19 9:41:34
     */
    @API(summary = "人脸信息列表接口",
            description = "人脸信息列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", in = InType.params, dataType = DataType.LONG, required = false),// true
                    @Param(name = "face", description = "人脸特征数组", in = InType.params, dataType = DataType.STRING, required = false),// true
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
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String face = MapUtils.getString(params, "face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = MapUtils.getString(params, "faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }

        params.put("page", page);
        List<FaceInfo> faceInfos = faceInfoService.listByParams4Page(params);
        return ResultUtil.getResult(faceInfos, page);
    }


    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary = "人脸信息列表导出接口",
            description = "人脸信息列表导出接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", in = InType.params, dataType = DataType.LONG, required = false),// true
                    @Param(name = "face", description = "人脸特征数组", in = InType.params, dataType = DataType.STRING, required = false),// true
            })
    @RequestMapping(value = "/export")
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
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String face = MapUtils.getString(params, "face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = MapUtils.getString(params, "faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }

        params.put("page", page);
        List<FaceInfo> list = faceInfoService.listByParams4Page(params);
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
        colTitle.put("userId", "用户Id");
        colTitle.put("face", "人脸特征数组");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            FaceInfo sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("userId", list.get(i).getUserId());
            map.put("face", list.get(i).getFace());
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

    //公司所有用户的用户id 用户名 还有人脸特征入库
    public static final List<FaceInfo> employeList = new ArrayList<>();

    @API(summary = "kq01 人脸特征缓存接口(启动自动执行)",
            description = "清空employeList然后\n" +
                    "读取所有faceinfo表中的数据," +
                    "将face的数据(是一个double数组)从字符串转成ary数组存入到faceinfo的faceAry字段," +
                    "然后将整个list 都缓存起来放入 静态变量employeList中以备后用",
            parameters = {
            })
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    @ResponseBody
    @PostConstruct
    public ResultDTO cache() throws Exception {
        employeList.clear();
        List<FaceInfo> list = faceInfoService.listByParams(new HashMap());
        for (FaceInfo faceInfo : list) {
            List<BigDecimal> faceFeture = JSON.parseArray(faceInfo.getFace(), BigDecimal.class);//人脸特征数组
            Double[] ary = new Double[128];
            for (int i = 0; i < faceFeture.size(); i++) {
                ary[i] = faceFeture.get(i).doubleValue();

            }
            SysUser user = sysUserService.getUserById(faceInfo.getUserId());
            faceInfo.setSysUser(user);
            faceInfo.setFaceAry(ary);
            employeList.add(faceInfo);
        }

        return this.getResult();

    }

//    @API(summary = "kq02 每秒人脸识别接口",
//            description = "//参考https://blog.csdn.net/nfmsr/article/details/78559930 " +
//                    "利用python 脚本获取摄像头图片每次获取一下图片 " +
//                    "并把名称用http接口方式调用接口 接口去访问磁盘上的图片 " +
//                    "并调用ai接口识别图片上的人脸 拿到人脸特征数组 " +
//                    "和库中的人脸进行比对 识别到后 记录到人脸考勤数据库中",
//            parameters = {
//            })
//    @RequestMapping(value = "/recognizeByFile")
//    @ResponseBody
//    public ResultDTO recognizeByFile(HttpServletRequest request, @RequestParam(value = "file") MultipartFile image) throws Exception {
//
//
//        HashMap map = new HashMap();
////        String filePath  = remoteCamera.capture();
//        BufferedImage img = ImageIO.read(image.getInputStream());
//        String fileName = System.currentTimeMillis() + "." + Config.getInstance().getImage().getType();
//        String uploadPath =
//                PathManager.getInstance().getWebRootPath().resolve(Config.getInstance().getImage().getServerDir()).resolve(FilePathUtil.getYMDPathAffix()).toString();
//        WebImageUtil.saveUploadFileToDisk(image, uploadPath.toString(), fileName);
//        String base64 = ImageUtil.ImageToBase64ByLocal(uploadPath + "/" + fileName);
//        String data = request.getParameter("data");
//        //logger.info(data);
//        map.put("image", URLEncoder.encode(base64));
//        map.put("maxFaceNum", "1");
//        map.put("faceFields", "embedding");
//        map.put("filename", DateUtil.getNow() + ".jpg");
//        String result = HttpRequestUtil.sendPost("http://127.0.0.1:8080/atomsrv/face/recog/face", map);
//        ResultDTO resultDTO = JsonUtil.toJavaBean(result, ResultDTO.class);
//        JSONObject jsonObject = (JSONObject) resultDTO.getData();
//        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);
//        if(finishTaskData.getResultNum()==0)
//            return this.getResult();
//        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
//            FinishTask finishTask = finishTaskData.getResult().get(i);
//            if (finishTask.getLocation().getWidth() < 100) continue;
//            Double[] thisMan = finishTask.getEmbedding();
//            double sum = 0;
//            for (int j = 0; j < FaceInfoController.employeList.size(); j++) {
//                FaceInfo faceInfo = FaceInfoController.employeList.get(j);
//                for (int k = 0; k < 128; k++) {
//                    sum += faceInfo.getFaceAry()[k] * thisMan[k];
//                }
//                if (sum > 0.9) {
//                    logger.info(faceInfo.getUserId() + "score" + sum);
//                    //查出这个人是谁 并插入一条考勤记录表
//                    CheckinOut checkinOut = new CheckinOut();
//                    checkinOut.setCheckTime(DateUtil.getNowTimeStamp());
//                    checkinOut.setUserId(faceInfo.getUserId());
//                    checkinOut.setCheckType(3);//人脸考勤
//                    checkinOutService.save(checkinOut);
//
//                    VirtualDoorService.open();//开门加上消息推送
//                    VirtualWeixinService.sendMsg(faceInfo.getSysUser().getUsername(), "摄像头签到成功");
//
//                    break;
//                }
//            }
//            System.out.println(sum);
//        }
//        return this.getResult();
//
//
//    }
    public static String accessToken ="";

    public void getAccessToken(){
        String url="http://192.168.188.8:3502/atomsrv/access_token";
        HashMap map =new HashMap();
        map.put("appid","0001");

        String currentTime =""+ System.currentTimeMillis()/1000;
        map.put("timestamp",currentTime);
        try {
            String token =MD5Util.getStringMD5String("0001_"+"Awifi_Ai_Biz_Image_Key_"+currentTime);
            map.put("token",token);
            String result = HttpRequestUtil.sendGet(url, map);//http://192.168.188.8:3502
            JSONObject jsonObject = (JSONObject)JSON.parse(result);
            JSONObject obj = (JSONObject)jsonObject.get("data");
            accessToken =obj.getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @API(summary = "kq02 每秒人脸识别接口",
            description = "//参考https://blog.csdn.net/nfmsr/article/details/78559930 " +
                    "利用python 脚本获取摄像头图片每次获取一下图片 " +
                    "并把名称用http接口方式调用接口 接口去访问磁盘上的图片 " +
                    "并调用ai接口识别图片上的人脸 拿到人脸特征数组 " +
                    "和库中的人脸进行比对 识别到后 记录到人脸考勤数据库中",
            parameters = {
            })
    @RequestMapping(value = "/recognize")
    @ResponseBody
    public ResultDTO recognizeByBase64(HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();
        String data = request.getParameter("data");
        //logger.info(data);
        map.put("image", data);
//        if(data.startsWith("b'")){
//            data=data.substring(2);
//        }

//       BufferedImage imageBuffer =  ImageUtil.base64ToImage(data);
//        ImageUtil.saveBase64Image("g://zzw",System.currentTimeMillis()+".png",data);
        String camera = request.getParameter("camera");
        map.put("maxFaceNum", "1");
        map.put("faceFields", "embedding");
//        map.put("filename", DateUtil.getNow() + ".jpg");
        faceInfoService.recognize(camera,data,map);
        return this.getResult();
    }


    @API(summary = "kq02 更新人脸之后算出这人的人脸特征",
            description = "当用户上传个人的头像后需要更新这个人的人脸特征"
                    ,
            parameters = {
            })
    @RequestMapping(value = "/updateByUserFace")
    @ResponseBody
    public ResultDTO updateByUserFace(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("userId");
        if(StringUtil.isBlank(userId))
            throw new BizException(50105125,"用户id不能为空");
        SysUser sysUser = sysUserService.getUserById(Long.valueOf(userId));
        if(sysUser== null)
            throw new BizException(50105128,"查无此用户");
        String faceUrl = sysUser.getFace();
        File file = PathManager.getInstance().getWebRootPath().resolve(faceUrl).toFile();
        if(!file.exists())
            throw new BizException(50105132,"查无此用户头像");
        String base64 = ImageUtil.ImageToBase64ByLocal(file.getAbsolutePath());
        HashMap postMap = new HashMap();
        //logger.info(data);
        postMap.put("image", base64);
        postMap.put("maxFaceNum", "1");
        postMap.put("faceFields", "embedding");
        postMap.put("filename", DateUtil.getNow() + ".jpg");
        getAccessToken();
        String result  = HttpRequestUtil.sendPost(ConfigUtil.getConfig("ai.face.recogize.url")+"?access_token="+accessToken, JsonUtil.toJson(postMap));
        //String result = HttpRequestUtil.sendPost("http://192.168.188.8:3502/atomsrv/face/recog/multi?access_token="+accessToken, map);//http://192.168.188.8:3502
        HashMap resultMap = JsonUtil.toJavaBean(result, HashMap.class);
        String code =MapUtils.getString(resultMap,"code") ;
        if (!code.equals("0")) {
            logger.error("人脸录入调用ai接口报错"+result);

            return this.getResult(50104148,result);
        }
        JSONObject jsonObject = (JSONObject) resultMap.get("data");
        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);
        if(finishTaskData.getResultNum()==0)
            return this.getResult();
        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
            FinishTask finishTask = finishTaskData.getResult().get(i);
            if (finishTask.getLocation().getWidth() < 20) continue;

            logger.info("file:"+file.getAbsolutePath()+"left:"+finishTask.getLocation().getLeft()+"top:"+finishTask.getLocation().getTop()+
                    "height:"+finishTask.getLocation().getHeight()+"width:"+finishTask.getLocation().getWidth());
            Double[] thisMan = finishTask.getEmbedding();

            String value = JSON.toJSONString(thisMan);
            FaceInfo faceInfo =new FaceInfo();
            faceInfo.setUserId(sysUser.getId());
            HashMap<String,Object> params  =new HashMap();

            params.put("userId",userId);
           List<FaceInfo >  faceInfoList = faceInfoService.listByParams(params);
            if(faceInfoList!=null && faceInfoList.size()>0){
                faceInfo= faceInfoList.get(0);
            }
            faceInfo.setRemark("{file:\""+file.getAbsolutePath()+"\",left:"+finishTask.getLocation().getLeft()+",top:"+finishTask.getLocation().getTop()+
                    "height:"+finishTask.getLocation().getHeight()+",width:"+finishTask.getLocation().getWidth()+"}");
            faceInfo.setFace(value);
            faceInfo.setName(sysUser.getUsername());
            faceInfoService.save(faceInfo);
            break;

            //faceInfo.getId();
            //  System.out.println(sum);
        }
        return this.getResult();
    }



    //http://192.168.213.7:8097/home/checkin/faceinfo/opendoor?camera=7099
    @RequestMapping(value = "/opendoor")
    @ResponseBody
    public ResultDTO testopendoor(HttpServletRequest request) throws Exception {
        VirtualDoorService.open(Integer.valueOf(request.getParameter("camera")));
        return this.getResult();
    }



}


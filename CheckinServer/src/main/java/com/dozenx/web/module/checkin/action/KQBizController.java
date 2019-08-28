package com.dozenx.web.module.checkin.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.DateUtil;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.MapUtils;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.module.checkin.MsgSubscribe;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualDoorService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualWeixinService;
import com.dozenx.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:13 2018/11/21
 * @Modified By:
 */

@APIs(description = "人脸信息")
@Controller
@RequestMapping("/checkin/face")

public class KQBizController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(KQBizController.class);
    @Resource
    private SysUserService sysUserService;

    @Resource
    private FaceCheckinOutService faceCheckinOutService;


    /**
     *
     * ai识别结果推送
     * person_id  用户所属文件夹的name
     * reg_patH 识别到的路径
     * db_img_path 识别的路径
     * max_score 最大分数
     * ave_score 平均分数
     * db_id 人脸库id 用户文件夹上一层 25 26
     * _cam_id 摄像头的rtsp地址
     * big_img_path 大图地址
     * Cost 识别时间
     */

    @API(summary = "kq05 人脸推送接口开发",
            description = "http://alpha-np.51awifi.com/home/checkin/face/receiveRegResult?access_token=xxxxx" +
                    "利用python 脚本获取摄像头图片每次获取一下图片 " +
                    "并把名称用http接口方式调用接口 接口去访问磁盘上的图片 " +
                    "并调用ai接口识别图片上的人脸 拿到人脸特征数组 " +
                    "和库中的人脸进行比对 识别到后 记录到人脸考勤数据库中",

            parameters = {
                    @Param(name = "cam_id", description = "摄像头id", in = InType.body, dataType = DataType.STRING, required = false),
//                    @Param(name = "person_id", description = "用户id", in = InType.body, dataType = DataType.STRING, required = true),
//                    @Param(name = "reg_path", description = "识别人脸图片url", in = InType.body, dataType = DataType.STRING, required = false),// false
//                    @Param(name = "db_img_path", description = "识别人脸图片url", in = InType.body, dataType = DataType.STRING, required = false),// false
//                    @Param(name = "score", description = "匹配的人脸url 底库url", in = InType.body, dataType = DataType.FLOAT, required = false),// true
//                    @Param(name = "ave_score", description = "人脸特征数组", in = InType.body, dataType = DataType.FLOAT, required = false),// true
//                    @Param(name = "corpId", description = "公司id", in = InType.body, dataType = DataType.STRING, required = false),// true

            })



    @RequestMapping(value = "/receiveRegResult")
    @ResponseBody
    public ResultDTO receiveRegResult(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        logger.info("接收到消息推送:  "+ System.currentTimeMillis());
        logger.info(JsonUtil.toJsonString(bodyParam));
        Long start = System.currentTimeMillis();
        String camera= MapUtils.getString(bodyParam, "cam_id");//cam_id rtsp流的地址 rtsp://admin:admin123@192.168.120.111:554
//        if(cameraId.indexOf("192.168.6.20")>=0 ){//硬编码 希望以后能改成 配置的形式
//            doorPort=7098;
//        }
        String userIdStr = MapUtils.getString(bodyParam, "person_id");
        String userAry[] = userIdStr.split("-");// zhangzhiwei_226
        String pinying = userAry[0];
        if (userAry.length > 1) {
            String userCode = userAry[1];
        }
        // Long userCode = Long.valueOf(userIdStr.split("-")[1]);
        String regPath = MapUtils.getString(bodyParam, "reg_path");
        ///home/yanxingjun/face_cy_4/face_sdk/data/reg_images/2019-04-30/person1/111328-513-503.jpg
//        regPath ="/mssrv/static"+ regPath.substring(regPath.indexOf("face_sdk")+"face_sdk".length());
        String regUrl = "/mssrv/static" + regPath.substring(regPath.indexOf("face_sdk") + "face_sdk".length());
        String dbImgPath = MapUtils.getString(bodyParam, "db_img_path");
//        dbImgPath ="/mssrv/static"+ dbImgPath.substring(dbImgPath.indexOf("face_sdk")+"face_sdk".length());
        String oriUrl = "/mssrv/static" + dbImgPath.substring(dbImgPath.indexOf("face_sdk") + "face_sdk".length());
        String score = MapUtils.getString(bodyParam, "max_score");
       // String avgScore = MapUtils.getString(bodyParam, "ave_score");
      ///  String corpId = MapUtils.getString(bodyParam, "db_id");//库id
        String _cam_id = MapUtils.getString(bodyParam, "_cam_id");//库id
        String bigImgPath = MapUtils.getString(bodyParam, "big_img_path");
        logger.info(userIdStr);
        Float cost = MapUtils.getFloat(bodyParam,"cost");
        //查询上次该code 的考勤时间
        //参数校验
        HashMap map = new HashMap();
        // map.put("code", userCode);//根据员工编号进行考勤的  需要记录下 上次改工号的考勤时间
        map.put("pinyin", pinying);
        SysUser sysUser = null;
        List<SysUser> sysUserList = sysUserService.listByParams(map);
        if (sysUserList == null || sysUserList.size() == 0) {
            logger.error("not find user 未找到用户 " + pinying);
            //   return this.getWrongResultFromCfg("err.USER_NOT_FOUND.code");
            map.put("pinyin", "admin");
            sysUserList = sysUserService.listByParams(map);
            sysUser = sysUserList.get(0);
        } else {
            logger.error("get the user by pinyin ("+pinying+") the result size is "+sysUserList.size()+" did need make modify in code add the condition of user code !!!!");
            sysUser = sysUserList.get(0);
        }
        if (sysUser == null) {
            return this.getWrongResultFromCfg("err.USER_NOT_FOUND.code");
        }
        FaceCheckinOut checkinOut = new FaceCheckinOut();
        checkinOut.setCheckTime(DateUtil.getNowTimeStamp());
//        checkinOut.setRemakr();
        checkinOut.setUserId(sysUser.getId());
        checkinOut.setCheckType(3);//人脸考勤
        checkinOut.setScore(Float.valueOf(score) / 10);//归到100制
        checkinOut.setBigImgPath(bigImgPath);
            //根据camera的rtsp地址找到camera的id
        logger.info("_cam_id"+_cam_id);
        checkinOut.setCamera(camera);   //摄像头rtsp地址
        checkinOut.setCameraId(Long.valueOf(_cam_id));  //摄像头id
        checkinOut.setUserName(sysUser.getUsername());  //从数据库中获取用户名
        checkinOut.setRegPath(regPath); //识别的图片的地址
        checkinOut.setOriPath(dbImgPath);   //原始人脸图片地址
        checkinOut.setCost(cost);   //耗时
        checkinOut.setRegUrl(regUrl);   //识别的url
        checkinOut.setOriUrl(oriUrl);checkinOut.setCheckTime(DateUtil.getNowTimeStamp());   //原始url

        //如果这个时间段已经有过人脸打卡了 那么就不人脸识别了
        faceCheckinOutService.save(checkinOut);

        //如果这个摄像头已经开过门了 就不再开门

        try {


            int doorPort = 7099;


            //从cameraId中截取ip地址
            logger.info(camera);
            System.out.println(camera);

            String regEx = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(camera);
//        while (m.find()) {
//            String result=m.group();
//           logger.info("ip 地址"+result);
////break;   加break则提取string中的一个IP
//        }
            m.find();
            String ip = m.group();//cameraId.substring(cameraId.indexOf("@")+1,cameraId.indexOf(":",cameraId.indexOf("@")));
            logger.info(ip);

            logger.info(ConfigUtil.getConfig("kq.camera_door_" + ip));

            if(StringUtil.isBlank(ConfigUtil.getConfig("kq.camera_door_" + ip))){
                logger.error("未维护摄像头与门的关系 door and camera relation kq.camera_door_" + ip);
            }else {
                doorPort = Integer.valueOf(ConfigUtil.getConfig("kq.camera_door_" + ip));


                if (StringUtil.isNotBlank(RedisUtil.get("kq_rece_result_"+ doorPort))) {

                }else{
                    RedisUtil.incr("kq_rece_result_" + doorPort, Integer.valueOf(ConfigUtil.getConfig("kq.chekcin.face.interval")));

                    logger.info("open door开门 cost:" + (System.currentTimeMillis() - start));


                    VirtualDoorService.open(doorPort);//开门加上消息推送
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //通过websocket 方式 或者其他的方式进行广播通知

        //broadcast msg
        //有多个方法等待消息的方式
        try {
            HashMap params = new HashMap();
            String onMsgMethod = ConfigUtil.getConfig("kq.receive.face.match.msg.receiver");
            if (StringUtil.isNotBlank(onMsgMethod)) {
                String[] onMsgMethodAry = onMsgMethod.split(",");
                for (String beanName : onMsgMethodAry) {
                    MsgSubscribe subscribe = (MsgSubscribe) BeanUtil.getBean(beanName);
                    if (subscribe != null) {
                        params.put("checkinOut", checkinOut);
                        subscribe.onMsg(params);
                    }
                }
            }
        }catch (Exception e){
            logger.error("",e);
        }

        logger.info("push weixin msg face recog 微信推送 " + sysUser.getUsername(), "识别成功");
        VirtualWeixinService.sendMsg(sysUser.getUsername(), "识别成功" + DateUtil.toDateStr(new Date(), "yyyy-MM-dd-HH:mm:ss"));
        //  break;

        logger.info("all cost:" + (System.currentTimeMillis()-start)+"   now timme:"+System.currentTimeMillis() );

        return this.getResult();
    }

    /**
     *
     * 测试人脸开门的url
     * @param request
     * @return
     * @throws Exception
     */
    //http://192.168.213.7:8097/home/checkin/faceinfo/opendoor?camera=7099
    @RequestMapping(value = "/opendoor")
    @ResponseBody
    public ResultDTO testopendoor(HttpServletRequest request) throws Exception {
        VirtualDoorService.open(Integer.valueOf(request.getParameter("camera")));
        return this.getResult();
    }


    /**
     * 说明:ajax请求FaceCheckinOut信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-11-21 16:53:17
     */
    @API(summary = "人脸抓拍接口",
            description = "人脸抓拍接口",
            parameters = {

                    @Param(name = "params", description = "参数{ \"pageSize\": \"11\", \"curPage\": \"1\", \"orgId\": 6,\"userName\": \"用户姓名\",\"camera\": \"摄像机\", \"checkTimeBegin\": \"2019-4-19 09:26:35 发生时间开始\", \"checkTimeEnd\": \"2019-4-19 09:26:35  发生时间结束\",}", in = InType.query, dataType = DataType.STRING, required = true),
//                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//                    @Param(name="id" , description="编号",in=InType.params,dataType = DataType.LONG,required =false),// false
//                    @Param(name="userId" , description="用户Id",in=InType.params,dataType = DataType.LONG,required =false),// true
//                    @Param(name="userName" , description="用户姓名",in=InType.params,dataType = DataType.STRING,required =false),// true
//                    @Param(name="camera" , description="摄像机编号",in=InType.params,dataType = DataType.STRING,required =false),// true
//                    @Param(name="checkType" , description="考勤类型",in=InType.params,dataType = DataType.INTEGER,required =false),// true
//                    @Param(name="checkTime" , description="创建时间",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
//                    @Param(name="score" , description="人脸匹配度",in=InType.params,dataType = DataType.FLOAT,required =false),// false
            })
    @APIResponse("{\n" +
            "\t\"r\": 0,\n" +
            "\t\"data\":[\n" +
            "    {\n" +
            "      \"id\": 6681,\n" +
            "      \"userId\": 1,//用户id\n" +
            "      \"userName\": \"superadmin\", //用户名称\n" +
            "      \"camera\": \"rtsp://admin:admin123@192.168.120.113:554\",//哪个摄像头\n" +
            "      \"checkType\": 3,\n" +
            "      \"checkTime\": \"2019-04-19T13:58:17.000+0000\", //检测到的时间\n" +
            "      \"score\": 828,  //匹配的分数\n" +
            "      \"cost\": null,//耗费的时间\n" +
            "      \"avgScore\": null,\n" +
            "      \"regUrl\": \" /data/reg_images/2019-04-19/chenyong-315/085817-828-828.jpg\",//识别的图片\n" +
            "      \"oriUrl\": \" /data/face_database/test/chenyong-315/0/original.jpg\",//原始的图片\n" +
            "      \"remark\": null,\n" +
            "      \"time\": null\n" +
            "    },….]\n" +
            "   \n" +
            "\"page\": {\n" +
            "\t\t\"curPage\": 1,\n" +
            "\t\t\"totalPage\": 1,\n" +
            "\t\t\"pageSize\": 10,\n" +
            "\t\t\"totalCount\": 1,\n" +
            "\t\t\"beginIndex\": 0,\n" +
            "\t\t\"hasPrePage\": false,\n" +
            "\t\t\"hasNextPage\": false\n" +
            "\t}\n" +
            "}\n")
    @RequestMapping(value = "/captrue/list", method = RequestMethod.GET)
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
        String userName = MapUtils.getString(params, "userName");
        if (!StringUtil.isBlank(userName)) {
            params.put("userName", userName);
        }
        String userNameLike = MapUtils.getString(params, "userNameLike");
        if (!StringUtil.isBlank(userNameLike)) {
            params.put("userNameLike", userNameLike);
        }
        String camera = MapUtils.getString(params, "camera");
        if (!StringUtil.isBlank(camera)) {
            params.put("camera", camera);
        }
        String cameraLike = MapUtils.getString(params, "cameraLike");
        if (!StringUtil.isBlank(cameraLike)) {
            params.put("cameraLike", cameraLike);
        }
        String checkType = MapUtils.getString(params, "checkType");
        if (!StringUtil.isBlank(checkType)) {
            params.put("checkType", checkType);
        }
        String checkTime = MapUtils.getString(params, "checkTime");
        if (!StringUtil.isBlank(checkTime)) {
            if (StringUtil.checkNumeric(checkTime)) {
                params.put("checkTime", checkTime);
            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = MapUtils.getString(params, "checkTimeBegin");
        if (!StringUtil.isBlank(checkTimeBegin)) {
            if (StringUtil.checkNumeric(checkTimeBegin)) {
                params.put("checkTimeBegin", checkTimeBegin);
            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = MapUtils.getString(params, "checkTimeEnd");
        if (!StringUtil.isBlank(checkTimeEnd)) {
            if (StringUtil.checkNumeric(checkTimeEnd)) {
                params.put("checkTimeEnd", checkTimeEnd);
            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String score = MapUtils.getString(params, "score");
        if (!StringUtil.isBlank(score)) {
            params.put("score", score);
        }

        params.put("page", page);
        List<FaceCheckinOut> faceCheckinOuts = faceCheckinOutService.listByParams4Page(params);
        return ResultUtil.getResult(faceCheckinOuts, page);
    }


}

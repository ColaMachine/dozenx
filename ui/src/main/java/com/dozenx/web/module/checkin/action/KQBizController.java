package com.dozenx.web.module.checkin.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.exception.BizException;
import com.dozenx.util.DateUtil;
import com.dozenx.util.ImageUtil;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.MapUtils;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.action.FaceInfoController;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualDoorService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualWeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Logger logger = LoggerFactory.getLogger(FaceInfoController.class);
    @Resource
    private SysUserService sysUserService ;

    @Resource
    private FaceCheckinOutService faceCheckinOutService;
    /**
     * 权限service
     **/


    @API(summary = "kq05 人脸推送接口开发",
            description = "http://alpha-np.51awifi.com/home/checkin/face/receiveRegResult?access_token=xxxxx" +
                    "利用python 脚本获取摄像头图片每次获取一下图片 " +
                    "并把名称用http接口方式调用接口 接口去访问磁盘上的图片 " +
                    "并调用ai接口识别图片上的人脸 拿到人脸特征数组 " +
                    "和库中的人脸进行比对 识别到后 记录到人脸考勤数据库中",

            parameters = {
                    @Param(name = "cam_id", description = "摄像头id",in= InType.body, dataType = DataType.STRING, required = false),
                    @Param(name = "person_id", description = "用户id",in= InType.body, dataType = DataType.STRING, required = true),
                    @Param(name = "reg_path", description = "识别人脸图片url",in= InType.body, dataType = DataType.STRING, required = false),// false
                    @Param(name = "db_img_path", description = "识别人脸图片url", in= InType.body,dataType = DataType.STRING, required = false),// false
                    @Param(name = "score", description = "匹配的人脸url 底库url", in= InType.body,dataType = DataType.FLOAT, required = false),// true
                    @Param(name = "ave_score", description = "人脸特征数组",in= InType.body, dataType = DataType.FLOAT, required = false),// true
                    @Param(name = "corpId", description = "公司id", in= InType.body,dataType = DataType.STRING, required = false),// true

            })
    @RequestMapping(value = "/receiveRegResult")
    @ResponseBody
    public ResultDTO recognizeByBase64(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        logger.info(JsonUtil.toJsonString(bodyParam));
        Long start = System.currentTimeMillis();
        String cameraId = MapUtils.getString(bodyParam,"cam_id");
        String userIdStr = MapUtils.getString(bodyParam,"person_id");

        if(userIdStr.indexOf("-")==-1){
            userIdStr+="-0";
        }
        String userName = userIdStr.split("-")[0];
        Long userCode = Long.valueOf(userIdStr.split("-")[1]);
        String regPath =MapUtils.getString(bodyParam,"reg_path");
        String dbImgPath = MapUtils.getString(bodyParam,"db_img_path");
        String score =MapUtils.getString(bodyParam,"max_score");
        String avgScore =MapUtils.getString(bodyParam,"ave_score");
        String corpId = MapUtils.getString(bodyParam,"db_id");//库id
        logger.info(userIdStr);

        //查询上次该code 的考勤时间
        //参数校验
        HashMap map =new HashMap();
        map.put("code",userCode);//根据员工编号进行考勤的  需要记录下 上次改工号的考勤时间
        List<SysUser> sysUserList = sysUserService.listByParams(map);
        if(sysUserList==null || sysUserList.size()==0){
            return this.getWrongResultFromCfg("err.USER_NOT_FOUND.code");
        }
        SysUser sysUser =sysUserList.get(0);
        if(sysUser==null){
            return this.getWrongResultFromCfg("err.USER_NOT_FOUND.code");
        }
        FaceCheckinOut checkinOut = new FaceCheckinOut();
        checkinOut.setCheckTime(DateUtil.getNowTimeStamp());
        checkinOut.setUserId(sysUser.getId());
        checkinOut.setCheckType(3);//人脸考勤
        checkinOut.setScore(Float.valueOf(score));
        checkinOut.setCamera(cameraId==null?"0":cameraId);
        checkinOut.setUserName(sysUser.getUsername());
        checkinOut.setRegUrl(regPath);
        checkinOut.setOriUrl(dbImgPath);
        //如果这个时间段已经有过人脸打卡了 那么就不人脸识别了
        faceCheckinOutService.save(checkinOut);
        try {
            logger.info("open door开门 cost:"+(System.currentTimeMillis()-start));
            VirtualDoorService.open(7099);//开门加上消息推送
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("push weixin msg face recog 微信推送"+sysUser.getUsername(), "识别成功");
        VirtualWeixinService.sendMsg(sysUser.getUsername(), "识别成功"+ DateUtil.toDateStr(new Date(), "yyyy-MM-dd-HH:mm:ss"));
      //  break;
        return this.getResult();
    }
}

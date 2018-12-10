/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.checkin.faceInfo.service;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.core.Path.PathManager;
import com.dozenx.util.*;
import com.dozenx.web.core.api.client.auth.http.util.AuthHttpRequest;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTask;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTaskData;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.action.FaceInfoController;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.checkin.faceInfo.bean.FaceInfo;
import com.dozenx.web.module.checkin.faceInfo.dao.FaceInfoMapper;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("faceInfoService")
public class FaceInfoService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(FaceInfoService.class);

    @Autowired
    FaceCheckinOutService faceCheckinOutService;
    @Resource
    private FaceInfoMapper faceInfoMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<FaceInfo> listByParams4Page(HashMap params) {
        return faceInfoMapper.listByParams4Page(params);
    }
    public List<FaceInfo> listByParams(HashMap params) {
        return faceInfoMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return faceInfoMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param FaceInfo
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(FaceInfo faceInfo) {
        // 进行字段验证
      /* ValidateUtil<FaceInfo> vu = new ValidateUtil<FaceInfo>();
        ResultDTO result = vu.valid(faceInfo);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (faceInfo.getId()==null ||  this.selectByPrimaryKey(faceInfo.getId())==null) {

            faceInfoMapper.insert(faceInfo);
        } else {
            faceInfoMapper.updateByPrimaryKeySelective(faceInfo);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Long  id){
        faceInfoMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public FaceInfo selectByPrimaryKey(Long id){
       return faceInfoMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            faceInfoMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

//    /**
//     * 提供缓存的获取所有的
//     * @return
//     */
//    public List<FaceInfo> getAll(){
//        List<FaceInfo> list = listByParams(new HashMap());
//        for(FaceInfo faceInfo : list){
//            List<BigDecimal> faceFeture = JSON.parseArray(faceInfo.getFace(),BigDecimal.class);//人脸特征数组
//            Double[] ary = new Double[128];
//            for(int i=0;i<faceFeture.size();i++){
//                ary[i] = faceFeture.get(i).doubleValue();
//
//            }
//            faceInfo.setFaceAry(ary);
//
//        }
//        return list;
//    }
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
        FaceInfoController.accessToken =obj.getString("access_token");

    } catch (Exception e) {
        e.printStackTrace();
    }


}
    @Async
    public void recognize(String camera,String data,HashMap map ){
      //  TokenServiceImpl tokenService = BeanUtil.getBean("tokenService";
        //getAccessToken();
        Map<String,Object> resultMap = AuthHttpRequest.sendPostRequest(ConfigUtil.getConfig("ai.face.recogize.url"),JsonUtil.toJson(map));
       // String result  = HttpRequestUtil.sendPost("http://192.168.188.8:3502/atomsrv/face/recog/multi?access_token="+FaceInfoController.accessToken, JsonUtil.toJson(map));
        //logger.info(result);
        //String result = HttpRequestUtil.sendPost("http://192.168.188.8:3502/atomsrv/face/recog/multi?access_token="+accessToken, map);//http://192.168.188.8:3502
       // HashMap resultMap = JsonUtil.toJavaBean(result, HashMap.class);
        String code = MapUtils.getString(resultMap,"code") ;


        if (!code.equals("0")) {
            String msg  = MapUtils.getString(resultMap,"msg");

            return ;
        }
        JSONObject jsonObject = (JSONObject) resultMap.get("data");
        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);
        if(finishTaskData.getResultNum()==0)
            return ;
        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
            FinishTask finishTask = finishTaskData.getResult().get(i);
            if (finishTask.getLocation().getWidth() < 10) continue;
            Double[] thisMan = finishTask.getEmbedding();

            for (int j = 0; j < FaceInfoController.employeList.size(); j++) {
                double sum = 0;
                FaceInfo faceInfo = FaceInfoController.employeList.get(j);

                if(System.currentTimeMillis()-faceInfo.lastCheckinTime<Integer.valueOf(ConfigUtil.getConfig("chekcin.face.interval"))*1000){
                    continue;//如果时间小于最小建个时间 不匹配
                }
                for (int k = 0; k < 128; k++) {
                    sum += faceInfo.getFaceAry()[k] * thisMan[k];
                }
                if (sum > 0.5) {
                    logger.info(faceInfo.getSysUser().getUsername()+faceInfo.getUserId() + "score" + sum);
                    //查出这个人是谁 并插入一条考勤记录表
                    FaceCheckinOut checkinOut = new FaceCheckinOut();
                    checkinOut.setCheckTime(DateUtil.getNowTimeStamp());
                    checkinOut.setUserId(faceInfo.getUserId());
                    checkinOut.setCheckType(3);//人脸考勤
                    checkinOut.setCamera("0");
                    checkinOut.setScore((float)sum);
                    checkinOut.setCamera(camera==null?"0":camera);
                    checkinOut.setUserName(faceInfo.getSysUser().getUsername());

                    //如果这个时间段已经有过人脸打卡了 那么就不人脸识别了
                    boolean hasFaceCheckIn = hasFaceCheckIn(faceInfo.getUserId());
                    faceCheckinOutService.save(checkinOut);

                    if(faceInfo.getId() == null){
                        logger.error("发生错误 faceInfo的id 为空");
                    }
                    try {
                        ImageUtil.saveBase64Image(PathManager.getInstance().getHomePath().resolve("checkin").toString(),checkinOut.getId()+".png",data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        logger.info("开门");
                        VirtualDoorService.open();//开门加上消息推送
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    logger.info("微信推送"+faceInfo.getSysUser().getUsername(), "识别成功");
                    VirtualWeixinService.sendMsg(faceInfo.getSysUser().getUsername(), "识别成功"+ DateUtil.toDateStr(new Date(), "yyyy-MM-dd-HH:mm:ss"));
                    break;
                }
                System.out.println(sum);
            }
            //  System.out.println(sum);
        }
    }
    public boolean hasFaceCheckIn(Long userId){
        return false;
        //根据时间判断是否是早上

        //吃饭前 睡完觉后

        //下班后


    }


}

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.module.checkin.faceInfo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.*;
import com.dozenx.web.core.api.client.auth.http.util.AuthHttpRequest;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTask;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTaskData;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.action.FaceInfoController;
import com.dozenx.web.module.checkin.faceInfo.bean.FaceInfo;
import com.dozenx.web.module.checkin.faceInfo.dao.FaceInfoMapper;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.service.PubImageService;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.ResultUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service("faceInfoService")
public class FaceInfoService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(FaceInfoService.class);
    @Autowired
    PubImageService pubImageService;
    @Autowired
    FaceCheckinOutService faceCheckinOutService;
    @Resource
    private FaceInfoMapper faceInfoMapper;

    /**
     * 说明:list by page and params根据参数返回列表
     *
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
     *
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
        if (faceInfo.getId() == null || this.selectByPrimaryKey(faceInfo.getId()) == null) {

            faceInfoMapper.insert(faceInfo);
        } else {
            faceInfoMapper.updateByPrimaryKeySelective(faceInfo);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * 说明:根据主键删除数据
     * description:delete by key
     *
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public void delete(Long id) {
        faceInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 说明:根据主键获取数据
     * description:delete by key
     *
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public FaceInfo selectByPrimaryKey(Long id) {
        return faceInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 多id删除
     *
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for (int i = 0; i < idAry.length; i++) {
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
    public void getAccessToken() {
        String url = "http://192.168.188.8:3502/atomsrv/access_token";
        HashMap map = new HashMap();
        map.put("appid", "0001");

        String currentTime = "" + System.currentTimeMillis() / 1000;
        map.put("timestamp", currentTime);
        try {
            String token = MD5Util.getStringMD5String("0001_" + "Awifi_Ai_Biz_Image_Key_" + currentTime);
            map.put("token", token);
            String result = HttpRequestUtil.sendGet(url, map);//http://192.168.188.8:3502
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            JSONObject obj = (JSONObject) jsonObject.get("data");
            FaceInfoController.accessToken = obj.getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Async
    public void recognize(String camera, String data, HashMap map) {
        Long start = System.currentTimeMillis();
        //  TokenServiceImpl tokenService = BeanUtil.getBean("tokenService";
        //getAccessToken();
        Map<String, Object> resultMap = AuthHttpRequest.sendPostRequest(ConfigUtil.getConfig("kq.ai.face.recogize.url"), JsonUtil.toJson(map));
        // String result  = HttpRequestUtil.sendPost("http://192.168.188.8:3502/atomsrv/face/recog/multi?access_token="+FaceInfoController.accessToken, JsonUtil.toJson(map));

        //logger.info(result);
        //String result = HttpRequestUtil.sendPost("http://192.168.188.8:3502/atomsrv/face/recog/multi?access_token="+accessToken, map);//http://192.168.188.8:3502
        // HashMap resultMap = JsonUtil.toJavaBean(result, HashMap.class);
        String code = MapUtils.getString(resultMap, "code");


        if (!code.equals("0")) {//如果调用ai接口不成功成功
            String msg = MapUtils.getString(resultMap, "msg");

            return;
        }
        JSONObject jsonObject = (JSONObject) resultMap.get("data");
        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);    //去的结果
        if (finishTaskData.getResultNum() == 0)
            return;            //如果没有识别到

        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
            FinishTask finishTask = finishTaskData.getResult().get(i);
            if (finishTask.getLocation().getWidth() < 10) continue;
            Double[] thisMan = finishTask.getEmbedding();
            double max = 0;
            FaceInfo theRightFace = null;
            for (int j = 0; j < FaceInfoController.employeList.size(); j++) {
                double sum = 0;
                FaceInfo faceInfo = FaceInfoController.employeList.get(j);

                if (System.currentTimeMillis() - faceInfo.lastCheckinTime < Integer.valueOf(ConfigUtil.getConfig("kq.chekcin.face.interval")) * 1000) {
                    continue;//如果时间小于最小建个时间 不匹配
                }
                for (int k = 0; k < 128; k++) {
                    sum += faceInfo.getFaceAry()[k] * thisMan[k];
                }
                if (sum > 0.5 && sum > max) {
                    theRightFace = faceInfo;
                    max = sum;
                }
                //System.out.println(sum);
            }

            if (theRightFace != null) {
                logger.info(DateUtil.getNow() + " " + theRightFace.getSysUser().getUsername() + theRightFace.getUserId() + "score" + max);
                //查出这个人是谁 并插入一条考勤记录表
                FaceCheckinOut checkinOut = new FaceCheckinOut();
                checkinOut.setCheckTime(DateUtil.getNowTimeStamp());
                checkinOut.setUserId(theRightFace.getUserId());
                checkinOut.setCheckType(3);//人脸考勤
                checkinOut.setCamera("0");
                checkinOut.setScore((float) max);
                checkinOut.setCamera(camera == null ? "0" : camera);
                checkinOut.setUserName(theRightFace.getSysUser().getUsername());
                theRightFace.lastCheckinTime = System.currentTimeMillis();
                //如果这个时间段已经有过人脸打卡了 那么就不人脸识别了
                boolean hasFaceCheckIn = hasFaceCheckIn(theRightFace.getUserId());
                faceCheckinOutService.save(checkinOut);

                if (theRightFace.getId() == null) {
                    logger.error("发生错误 faceInfo的id 为空");
                }
                try {
                    ImageUtil.saveBase64Image(PathManager.getInstance().getHomePath().resolve("checkin").toString(), checkinOut.getId() + ".png", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    logger.info("open door开门 cost:" + (System.currentTimeMillis() - start));
                    VirtualDoorService.open(Integer.valueOf(camera));//开门加上消息推送
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("push weixin msg face recog 微信推送" + theRightFace.getSysUser().getUsername(), "识别成功");
                VirtualWeixinService.sendMsg(theRightFace.getSysUser().getUsername(), "识别成功" + DateUtil.toDateStr(new Date(), "yyyy-MM-dd-HH:mm:ss"));
                break;
            }
            //  System.out.println(sum);
        }
    }

    public boolean hasFaceCheckIn(Long userId) {
        return false;
        //根据时间判断是否是早上

        //吃饭前 睡完觉后

        //下班后


    }

    public void batchUpdate(Long userId, String userNamePinyin, List<String> uploadFaceList, boolean allForceChange) {
        //删除这个人的所有face信息
        //这里有两个集合 一个是老的 imgUrl 集合  还有一个是 新的imgUrl 集合
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);


        //删除需要删除的人脸信息 添加需要添加的人脸信息

        //中间分析结果
        List<String> oldFaceUrlList = new ArrayList<>();     //原先的照片列表
        List<String> newAddUrlList = new ArrayList<>();          //


        //最终分析结果
        List<FaceInfo> oldFaceInfoList = listByParams(params);       //原有的的faceInfo
        List<FaceInfo> needDeleteFaceInfo = new ArrayList<>();   //需要删除的faceInfo
        List<FaceInfo> needAddFaceInfo = new ArrayList<>();     //需要添加的faceInfo
        List<FaceInfo> needStayFaceInfo = new ArrayList<>();    //需要保留的faceInfo

        List<FaceInfo> nowFaceInfoList = new ArrayList<>();    //需要保留的faceInfo

        for (FaceInfo faceInfo : oldFaceInfoList) {
            oldFaceUrlList.add(faceInfo.getFace());
            if (uploadFaceList.contains(faceInfo.getFace())) {
                needStayFaceInfo.add(faceInfo);
            } else

            //如果找到了 就保留 没有的话就删除
            {
                needDeleteFaceInfo.add(faceInfo);
            }
        }


        newAddUrlList.addAll(uploadFaceList);
        newAddUrlList.removeAll(oldFaceUrlList);

        //新增的头像
        for (String url : newAddUrlList) {
            FaceInfo faceInfo = new FaceInfo();
            faceInfo.setUserId(userId);
            faceInfo.setName(userNamePinyin);
            PubImage pubImage = pubImageService.getPubImageByFileName(url);
            faceInfo.setUrl(pubImage.getRelPath() + "/" + pubImage.getName());
            faceInfo.setFace(pubImage.getRelPath() + "/" + pubImage.getName());
            faceInfo.setAbsPath(pubImage.getAbsPath());

//            String thridImageId = thirdFaceService.add(sysGroup.getOutId().intValue(), ImageUtil.ImageToBase64ByLocal(pubImage.getAbsPath()), null, userName, null, sessionUser.getAccount(), sessionUser.getPwd());
//            faceInfo.setRemark(thridImageId);
//            faceInfo.setOutId(Long.valueOf(thridImageId));
            //将face 转移到指定的目录 和具体的文件名称
//            faceInfoService.save(faceInfo);
            needAddFaceInfo.add(faceInfo);
        }

        if (allForceChange) {
            needDeleteFaceInfo.addAll(oldFaceInfoList);
            needDeleteFaceInfo.addAll(needAddFaceInfo);
            needAddFaceInfo.addAll(needStayFaceInfo);

        }

        //先删除要删除的
        for (FaceInfo faceInfo : needDeleteFaceInfo) {
            delete(faceInfo.getId());
            // thirdFaceService.delete(CastUtil.toString(faceInfo.getOutId()), sessionUser.getAccount(), sessionUser.getPwd());
        }
        //再添加要添加的
        for (FaceInfo faceInfo : needAddFaceInfo) {
            //   String thridImageId = thirdFaceService.add(sysGroup.getOutId().intValue(), ImageUtil.ImageToBase64ByLocal(faceInfo.getAbsPath()), null, userName, null, sessionUser.getAccount(), sessionUser.getPwd());
            //  faceInfo.setOutId(Long.valueOf(thridImageId));
            save(faceInfo);
        }

    }

    public FaceInfo getByOutId(Long outId) {
        return faceInfoMapper.getByOutId(outId);
    }

    public void arrangementUserNameCode0123original(String parentDir, Long userId, String userName, String code, List<String> uploadFaceList) throws IOException {

        //拿到group id 在哪里
        String userName_code = userName + "_" + code;
        File file = new File(parentDir);
        if (file.mkdirs()) {

        }
        //将所有的照片都移动到新的库下面
        //照片整理 按章指定的目录
        // 获取根目录
        Path parentPath = Paths.get(parentDir);

        String fileName = "original.jpg";
        List<String> faceList = new ArrayList<>();
        logger.info("删除文件夹");
        String userName_code_realPath = parentPath + File.separator + userName_code;    // zhangzhiwei-A0226/0/
        File userName_code_file = new File(userName_code_realPath);
       // FileUtils.cleanDirectory(parentPath.toFile());
        //将图片挨个移动过去
        for (int i = 0, size = uploadFaceList.size(); i < size; i++) {
            String uploadFaceUrl = uploadFaceList.get(i);
            String userName_code_index = userName_code + "-temp" + File.separator + i;

            PubImage pubImage = pubImageService.getPubImageByFileName(uploadFaceUrl);
            FileUtil.copyFile(new File(pubImage.getAbsPath()), new File(parentDir + "/" + userName_code_index + "/" + fileName));
            // String rootfaceUrl = UploadFaceUtil.mkdirFace(groupid_realpath, uploadFaceUrl, userName_code_index, originalJpg);    //  将上传的url 传送到磁盘里面   这里有一个问题就是如果删除了的照片 这里是找不到的 肯定会报错
            //    faceList.add(rootfaceUrl);
        }

        FileUtil.deleteDir(userName_code_file);       //删除整个文件夹
        File tempFile = new File(userName_code_realPath + "-temp");
        tempFile.renameTo(userName_code_file);
    }
}

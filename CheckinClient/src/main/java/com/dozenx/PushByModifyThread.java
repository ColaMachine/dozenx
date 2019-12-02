package com.dozenx;

import com.dozenx.service.CheckinOutService;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description: 检测考勤数据库是否有更新 如果有更新就查出新的数据推送给服务器
 * @Date: Created in 15:01 2018/11/6
 * @Modified By:
 */
public class PushByModifyThread implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(PushByModifyThread.class);
    public Long lastModifyedTime = System.currentTimeMillis();//程序启动时间the program start time
    CheckinOutService checkinOutService;

    public PushByModifyThread(CheckinOutService service) {
        this.checkinOutService = service;
    }

    @Override
    public void run() {
//        logger.info("PushByModifyThread run");
        File mdbFile = null;
        try {
            mdbFile = new File(PropertiesUtil.get("access.path"));

          ///  if (mdbFile.lastModified() > lastModifyedTime) {

                logger.info("======考勤数据有新数据 kaoqing access file has been updated ");
                //查询上次到现在的数据 推送给服务器
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(lastModifyedTime);
//                Date start = calendar.getTime();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                Date end = calendar.getTime();
                List<CheckInOut> checkInOuts = checkinOutService.getCheckInOuts(System.currentTimeMillis()-4*24*60*60*1000, System.currentTimeMillis()+50000);
                lastModifyedTime = mdbFile.lastModified();

                if (checkInOuts == null || checkInOuts.size() == 0) {
                    logger.info("======kaoqing list is empty 数据为空 ======== ");
                    return;
                }

                for(CheckInOut logItem : checkInOuts){
                    String userName = checkinOutService.id2NameMap.get(logItem.getUserId());
                   logger.info("打卡者:"+userName) ;
                    System.out.println("打卡者:"+userName);
                }
                HashMap postmap = new HashMap();
                String jsonStr= JsonUtil.toJsonString(checkInOuts);
                postmap.put("params", jsonStr);

                logger.info("======准备推送新考情数据 send newest kaoqing data "+jsonStr);
                HttpRequestUtil.sendPost(PropertiesUtil.get("server.url")+PropertiesUtil.get("checkin.sync.url"), postmap);
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

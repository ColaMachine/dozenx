package com.dozenx;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.PropertiesUtil;
import com.dozenx.service.CheckinOutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:01 2018/11/6
 * @Modified By:
 */
public class PushBy10minThread implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(PushBy10minThread.class);
    public Long lastModifyedTime = System.currentTimeMillis();//程序启动时间the program start time
    CheckinOutService checkinOutService;

    public PushBy10minThread(CheckinOutService service) {
        this.checkinOutService = service;
    }

    @Override
    public void run() {
        logger.info("PushBy10minThread run");
        Calendar calendar = Calendar.getInstance();
        String nowDate = DateUtil.toDateStr(calendar.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        calendar.add(Calendar.HOUR, -48);
        String startDate = DateUtil.toDateStr(calendar.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        try {
            List<CheckInOut> checkInOuts = checkinOutService.getCheckInOuts(startDate, nowDate);
            //用户早上8:30分考勤记录是否存在

            if (checkInOuts == null || checkInOuts.size() == 0) {
                //logger.info("数据为空");
                return;
            }
            HashMap<String, String> checkInOutMap = new HashMap<String, String>();

            //推送数据
            HashMap postmap = new HashMap();
            String jsonStr =JsonUtil.toJsonString(checkInOuts);
            logger.info("准备发送考情数据:"+jsonStr);
            postmap.put("params", jsonStr);
            HttpRequestUtil.sendPost(PropertiesUtil.get("server.url")+PropertiesUtil.get("checkin.sync.url"), postmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

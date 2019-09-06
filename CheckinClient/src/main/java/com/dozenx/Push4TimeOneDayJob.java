package com.dozenx;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:01 2018/11/6
 * @Modified By:
 */
public class Push4TimeOneDayJob implements Job {
    static final Logger logger = Logger.getLogger(Push4TimeOneDayJob.class);
    public Long lastModifyedTime = System.currentTimeMillis();//程序启动时间the program start time



    public void run() {
        logger.info("Push4TimeOneDayJob run");
        String startDate = DateUtil.toDateStr( DateUtil.getDayBegin(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        String endDate = DateUtil.toDateStr( DateUtil.getDayEnd(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        logger.info("now date :"+startDate+" startDate" +endDate);
        try {
            List<CheckInOut> checkInOuts = ReadAccess.checkinOutService.getCheckInOuts(startDate, endDate);
            //如果数据是空的就不推送了
            if (checkInOuts == null || checkInOuts.size() == 0) {
                return;
            }
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

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.run();
    }
}

package com.dozenx;

import com.dozenx.service.CheckinOutService;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.PropertiesUtil;
import com.dozenx.common.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:01 2018/11/6
 * @Modified By:
 */
public class PushByChidaoThread implements Runnable {
    static final Logger logger = Logger.getLogger(PushByChidaoThread.class);
    public Long lastModifyedTime = System.currentTimeMillis();//程序启动时间the program start time
    CheckinOutService checkinOutService;
    String timeStrAry[] = null;

    public PushByChidaoThread(CheckinOutService service) throws Exception {
        String timeStr =  PropertiesUtil.get("late.check.time");
        //String timeStr = "08:30-06:00-08:31,12:18-11:50-12:21,13:50-13:20-13:51,18:30-17:30-18:30";
        timeStrAry = timeStr.split(",");
        this.checkinOutService = service;
    }

    @Override
    public void run() {
       // logger.info("PushByChidaoThread run");
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return;//如果是双休日不提醒
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String begin = null;
        String end = null;
        //TODO delete
        String date = DateUtil.getTodayDate();
        for (int i = 0; i < timeStrAry.length; i++) {
            String hourAndMin[] = timeStrAry[i].split("-")[0].split(":");
            if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {//正好在这个点上面 就进行验证

            } else {//否则到下个点去校验
                //TODO delete
                continue;
            }
            begin = timeStrAry[i].split("-")[1];
            end = timeStrAry[i].split("-")[2];
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]), Integer.valueOf(end.split(":")[0]), Integer.valueOf(end.split(":")[1]));
//                calendar1.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hourAndMin[0]));
//                calendar1.set(Calendar.MINUTE,Integer.valueOf(hourAndMin[1]));
//            if (calendar1.getTimeInMillis() < System.currentTimeMillis()) {
//                //TODO delete
//                break;
//            }
//            if (DateUtil.parseToDate(date + " " + end + ":00", "yyyy-MM-dd HH:mm:ss").getTime() > System.currentTimeMillis()) {
//                //TODO delete
//                break;
//            }
            logger.info("开始查询" + date + " " + begin + " " + end + "");
            process(date, begin, end, hour + ":" + minute);
            //break;
            //  } else {
            //  }
        }
        if (begin == null) {
            // continue;
        }
    }


    public void process(String date, String begin, String end, String now) {
        //   String timeStr = "08:28-06:00-08:30,12:18-11:50-12:20,01:50-01:20-01:50,18:00-17:30-18:00";
        logger.info(date + " " + begin + " " + end);
//        String propertiesPath = PathManager.getInstance().getClassPath().resolve("properties/config.properties").toString();
//        logger.info("properties file path" + propertiesPath);
//        PropertiesUtil.load(propertiesPath);
        // ReadAccess readAccess = new ReadAccess();
        try {
            // readAccess.init(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));//开始连接数据库

            //readAccess.initConnection2Access(new File(PropertiesUtil.get("access.path")));//开始连接数据库
            //      String timeStrAry[] = timeStr.split(",");


            //Calendar calendar = Calendar.getInstance();


            //查询整个事件段的考勤数据
            // String dateStr = getTodayDate();
            // String dateStr = "2018-10-17";
            List<CheckInOut> checkInOuts = checkinOutService.getCheckInOuts(date + " " + begin + ":00", date + " " + end + ":00");
            //用户早上8:30分考勤记录是否存在

            if (checkInOuts == null || checkInOuts.size() == 0) {
                logger.info("数据为空");
                return;
            }
            HashMap<String, String> checkInOutMap = new HashMap<String, String>();

            //推送数据
//            HashMap postmap = new HashMap();
//            postmap.put("params", JsonUtil.toJsonString(checkInOuts));
//            HttpRequestUtil.sendPost("http://192.168.120.80/home/checkin/sync", postmap);
            for (int i = 0; i < checkInOuts.size(); i++) {
                CheckInOut checkInOut = checkInOuts.get(i);

//                logger.info(checkInOut.getUserId() + " " + checkInOut.getCheckTime());
                if (StringUtil.isNotBlank(checkInOut.getUserId())) {
//                    UserInfo userInfo =id2UserMap.get(checkInOut.getUserId().trim());
//                    if(userInfo==null)
//                        continue;
                    String userName = checkinOutService.id2NameMap.get(checkInOut.getUserId());
                    if (StringUtil.isBlank(userName)) {
                        continue;
                    } else {
                        checkInOutMap.put(userName, checkInOut.getCheckTime());
                        // logger.info(userName + "考勤" + checkInOut.getCheckTime());
                        // System.out.println(userName + "考勤" + checkInOut.getCheckTime());
                    }
                }
            }
            StringBuffer sb = new StringBuffer();
            for (UserInfo userInfo : checkinOutService.alreadyUser) {
                //  if (alreadyUser.get(userInfo.getName()) != null) {
                if (checkInOutMap.get(userInfo.getName()) == null) {
                    sb.append(userInfo.getName() + " ");
                    logger.info(userInfo.getUserId() + " " + userInfo.getName() + "还没有考勤" + date + " " + begin + ":00 " + date + " " + end + ":00");
                    //  logger.info(userInfo.getUserId() + " " + userInfo.getName() + "还没有考勤"+date + " " + begin + ":00 "+date + " " + end + ":00");

                }
                // }
            }
            String result = sb.toString();
            if (StringUtil.isNotBlank(result)) {
                HashMap map = new HashMap();
                map.put("params", result);
                HttpRequestUtil.sendPost( PropertiesUtil.get("server.url") + PropertiesUtil.get("late.notify.url"), map);
            }

            //EmailUtil.send("371452875@qq.com", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
    }

}

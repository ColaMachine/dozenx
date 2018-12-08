package com.dozenx;


import com.dozenx.core.Path.PathManager;
import com.dozenx.service.CheckinOutService;
import com.dozenx.util.PropertiesUtil;
import com.dozenx.util.StringUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.quartz.JobBuilder.newJob;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 12:30 2018/10/17
 * @Modified By:
 */
public class ReadAccess {
    static final Logger logger = Logger.getLogger(ReadAccess.class);
//    Connection conn;


    /**
     * TODO : 读取文件access
     *
     * @return
     * @throws ClassNotFoundException
     */
//    public List<CheckInOut> getCheckInOuts(String begin, String end) {
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        String tableName = "CHECKINOUT";
//        try {
//            stmt = conn.prepareStatement("select * from CHECKINOUT  where CHECKTIME between ? and ? ");
//            // 读取第一个表的内容
//            stmt.setDate(1, new java.sql.Date(parseToDate(begin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            stmt.setDate(2, new java.sql.Date(parseToDate(end, "yyyy-MM-dd HH:mm:ss").getTime()));
//            rs = stmt.executeQuery();
//            ResultSetMetaData data = rs.getMetaData();
//            List<CheckInOut> list = new ArrayList<CheckInOut>();
//
//            while (rs.next()) {
//                // for (int i = 1; i <= data.getColumnCount(); i++) {
//                CheckInOut checkInOut = new CheckInOut();
//                checkInOut.setUserId(rs.getString("USERID"));
//                checkInOut.setCheckTime(rs.getString("CHECKTIME").substring(0, 19));
//                checkInOut.setCheckType(rs.getString("CHECKTYPE").equals("I") ? 1 : 0);
//                list.add(checkInOut);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

//    public static Date parseToDate(String s, String format) {
//        DateFormat df = new SimpleDateFormat(format);
//        try {
//            return df.parse(s);
//        } catch (ParseException e) {
//            // e.printStackTrace();
//            //System.err.println(s + "日期格式不对");
//            return null;
//        }
//    }
//
//    List<UserInfo> alreadyUser = null;
//    HashMap<String, String> id2NameMap = new HashMap();

//    public void initUser() throws IOException {
//
//
//        //查找用户信息 这一步从access 读取出用户的姓名和id 的列表数据
//        List<UserInfo> usersFromAccess = this.getUserListFromAccess();
//        //这一步从excel 读取出用户的基本信息数据 主要以姓名为主键 然后从access的数据里提取出userid
//        alreadyUser = this.getUserListFromExcel();
//        //姓名对应的id
//        HashMap<String, String> name2idMap = new HashMap();
//
//
//        for (UserInfo userInfo : usersFromAccess) {
//            name2idMap.put(userInfo.getName(), userInfo.getUserId());
//            id2NameMap.put(userInfo.getUserId(), userInfo.getName());
//        }
//
//
//        //id对应的用户信息
//        HashMap<String, UserInfo> id2UserMap = new HashMap();
//        List<HashMap<String, String>> list = new ArrayList<>();
//        //excel的数据再补充userid 从数据库中
//        for (UserInfo userInfo : alreadyUser) {
//            userInfo.setUserId(name2idMap.get(userInfo.getName()));//补冲userid数据
//            id2UserMap.put(userInfo.getUserId(), userInfo);
//            HashMap<String, String> map = new HashMap<>();
//            if (StringUtil.isBlank(userInfo.getUserId()))
//                continue;
//            map.put("outId", userInfo.getUserId());
//            map.put("username", userInfo.getName());
//            map.put("telno", userInfo.getTelno());
//            map.put("email", userInfo.getEmail());
//            map.put("account", userInfo.getEmail());
//            map.put("remark", userInfo.getCode());
//            list.add(map);
//        }
//
//        //将用户端的用户推送给服务器端
//        try {
//            HashMap map = new HashMap();
//            map.put("params", JsonUtil.toJson(list));
//            HttpRequestUtil.sendPost(PropertiesUtil.get("synuser.url"), map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    //"2018/10/17 06:00:00", "2018/10/17 08:30:59"
    //"2018/10/17 06:00:00", "2018/10/17 08:30:59"
    public static void main(String[] args) {
        ReadAccess readAccess = new ReadAccess();
        // readAccess.init(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));//开始连接数据库
        readAccess.mainLoop();
        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
    }

    File mdbFile = null;
    public static CheckinOutService checkinOutService = new CheckinOutService();

    public void mainLoop() {
        try {
            String propertiesPath = PathManager.getInstance().getClassPath().resolve("properties/config.properties").toString();
            logger.info("properties file path" + propertiesPath);
            PropertiesUtil.load(propertiesPath);
            Calendar calendar = Calendar.getInstance();
            mdbFile = new File(PropertiesUtil.get("access.path"));
//            CheckinOutService checkinOutService =new CheckinOutService();
            checkinOutService.initConnection2Access(mdbFile);
            // initConnection2Access(mdbFile);//开始连接数据库
//            checkinOutService.initUser();//根据userInfo 获取有效用户
            ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
            // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
            service.scheduleAtFixedRate(
                    new PushByModifyThread(checkinOutService), 60,
                    5, TimeUnit.SECONDS);
//            // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
//
//
            service.scheduleWithFixedDelay(
                    new PushBy10minThread(checkinOutService), 1,
                    8 * 60, TimeUnit.SECONDS);//
////            service.scheduleWithFixedDelay(
////                    new PushUserThread(checkinOutService), 1,
////                    *60*60, TimeUnit.SECONDS);//
//            service.scheduleWithFixedDelay(
//                    new PushByChidaoThread(checkinOutService), 1,
//                    60, TimeUnit.SECONDS);//1分钟确保只执行一次


//            Long lastTime = calendar.getTimeInMillis();
//            while (true) {
//                calendar = Calendar.getInstance();
//                if (calendar.getTimeInMillis() - lastTime > 9 * 60 * 1000) {
//                    processLast10min();//10分钟执行一次 推送数据到server
//                }
//
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


            //创建scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //定义一个Trigger
            String timeStr = PropertiesUtil.get("late.check.time");
            String[] timeSecAry = timeStr.split(",");


            for (int i = 0; i < timeSecAry.length; i++) {
                String timeSec = timeSecAry[i];
                if (StringUtil.isBlank(timeSec)) {
                    continue;
                }
                String[] shijianFanWeiAry = timeSec.split("-");
                if (shijianFanWeiAry.length == 0) {
                    logger.error("late.check.time is error format" + timeStr);
                }

                String[] time = shijianFanWeiAry[0].split(":");

                JobDetail job = newJob(Push4TimeOneDayJob.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                        .withIdentity("job" + i, "group1") //定义name/group
                        .usingJobData("name", timeSec) //定义属性
                        .build();

                //创建一trigger
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + i, "group_2")
                        .startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 " + time[1] + " " + time[0] + " * * ?")).build();


                scheduler.scheduleJob(job, trigger);
            }

//            Trigger trigger8 = cronSchedule("0 31 8 * * ?") // 每天8:00-17:00，每隔2分钟执行一次
//                    .build();
//            Trigger trigger12 = cronSchedule("0 21 12 * * ?") // 每天8:00-17:00，每隔2分钟执行一次
//                    .build();
//            Trigger trigger13 = cronSchedule("0 51 13 * * ?") // 每天8:00-17:00，每隔2分钟执行一次
//                    .build();
//            Trigger trigger23 = cronSchedule("0 55 23 * * ?") // 每天8:00-17:00，每隔2分钟执行一次
//                    .build();


//            scheduler.scheduleJob(job, trigger8);
//
//            scheduler.scheduleJob(job, trigger12);
//
//            scheduler.scheduleJob(job, trigger13);
//
//            scheduler.scheduleJob(job, trigger23);

            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void sanshijianduanLoop(String[] timeStrAry) {
//
//        Calendar calendar = Calendar.getInstance();
//
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//
//        String begin = null;
//        String end = null;
//        //TODO delete
//        String date = "2018-09-17";//DateUtil.getTodayDate();
//        for (int i = 0; i < timeStrAry.length; i++) {
//            String hourAndMin[] = timeStrAry[i].split("-")[0].split(":");
//            if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {//正好在这个点上面 就进行验证
//
//            } else {//否则到下个点去校验
//                //TODO delete
//                //continue;
//            }
//            begin = timeStrAry[i].split("-")[1];
//            end = timeStrAry[i].split("-")[2];
//            Calendar calendar1 = Calendar.getInstance();
//            calendar1.set(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]), Integer.valueOf(end.split(":")[0]), Integer.valueOf(end.split(":")[1]));
////                calendar1.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hourAndMin[0]));
////                calendar1.set(Calendar.MINUTE,Integer.valueOf(hourAndMin[1]));
//            if (calendar1.getTimeInMillis() < System.currentTimeMillis()) {
//                //TODO delete
//                //break;
//            }
//            if (DateUtil.parseToDate(date + " " + end + ":00", "yyyy-MM-dd HH:mm:ss").getTime() > System.currentTimeMillis()) {
//                //TODO delete
//                // break;
//            }
//            process(date, begin, end, hour + ":" + minute);
//            //break;
//            //  } else {
//            //  }
//        }
//        if (begin == null) {
//            // continue;
//        }
//    }


//    public void pushByFileLastModify() {
//        if (mdbFile.lastModified() > lastModifyedTime) {
//
//
//            //查询上次到现在的数据 推送给服务器
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(lastModifyedTime);
//            Date start = calendar.getTime();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            Date end = calendar.getTime();
//            List<CheckInOut> checkInOuts = this.getCheckInOuts(DateUtil.toDateStr(start, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toDateStr(start, DateUtil.YYYY_MM_DD_HH_MM_SS));
//            lastModifyedTime = mdbFile.lastModified();
//
//            if (checkInOuts == null || checkInOuts.size() == 0) {
//                logger.info("数据为空");
//                return;
//            }
//            HashMap postmap = new HashMap();
//            postmap.put("params", JsonUtil.toJsonString(checkInOuts));
//            HttpRequestUtil.sendPost("http://192.168.120.80/home/checkin/sync", postmap);
//        }
//
//    }

//    public void processLast10min() {
//        Calendar calendar = Calendar.getInstance();
//        String nowDate = DateUtil.toDateStr(calendar.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
//        calendar.add(Calendar.MINUTE, -10);
//        String startDate = DateUtil.toDateStr(calendar.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
//        try {
//            List<CheckInOut> checkInOuts = this.getCheckInOuts(startDate, nowDate);
//            //用户早上8:30分考勤记录是否存在
//
//            if (checkInOuts == null || checkInOuts.size() == 0) {
//                logger.info("数据为空");
//                return;
//            }
//            HashMap<String, String> checkInOutMap = new HashMap<String, String>();
//
//            //推送数据
//            HashMap postmap = new HashMap();
//            postmap.put("params", JsonUtil.toJsonString(checkInOuts));
//            HttpRequestUtil.sendPost("http://192.168.120.80/home/checkin/sync", postmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void process(String date, String begin, String end, String now) {
//        //   String timeStr = "08:28-06:00-08:30,12:18-11:50-12:20,01:50-01:20-01:50,18:00-17:30-18:00";
//        logger.info(date + " " + begin + " " + end);
////        String propertiesPath = PathManager.getInstance().getClassPath().resolve("properties/config.properties").toString();
////        logger.info("properties file path" + propertiesPath);
////        PropertiesUtil.load(propertiesPath);
//        // ReadAccess readAccess = new ReadAccess();
//        try {
//            // readAccess.init(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));//开始连接数据库
//
//            //readAccess.initConnection2Access(new File(PropertiesUtil.get("access.path")));//开始连接数据库
//            //      String timeStrAry[] = timeStr.split(",");
//
//
//            //Calendar calendar = Calendar.getInstance();
//
//
//            //查询整个事件段的考勤数据
//            // String dateStr = getTodayDate();
//            // String dateStr = "2018-10-17";
//            List<CheckInOut> checkInOuts = this.getCheckInOuts(date + " " + begin + ":00", date + " " + end + ":00");
//            //用户早上8:30分考勤记录是否存在
//
//            if (checkInOuts == null || checkInOuts.size() == 0) {
//                logger.info("数据为空");
//                return;
//            }
//            HashMap<String, String> checkInOutMap = new HashMap<String, String>();
//
//            //推送数据
////            HashMap postmap = new HashMap();
////            postmap.put("params", JsonUtil.toJsonString(checkInOuts));
////            HttpRequestUtil.sendPost("http://192.168.120.80/home/checkin/sync", postmap);
//            for (int i = 0; i < checkInOuts.size(); i++) {
//                CheckInOut checkInOut = checkInOuts.get(i);
//
////                logger.info(checkInOut.getUserId() + " " + checkInOut.getCheckTime());
//                if (StringUtil.isNotBlank(checkInOut.getUserId())) {
////                    UserInfo userInfo =id2UserMap.get(checkInOut.getUserId().trim());
////                    if(userInfo==null)
////                        continue;
//                    String userName = id2NameMap.get(checkInOut.getUserId());
//                    if (StringUtil.isBlank(userName)) {
//                        continue;
//                    } else {
//                        checkInOutMap.put(userName, checkInOut.getCheckTime());
//                        // logger.info(userName + "考勤" + checkInOut.getCheckTime());
//                        // System.out.println(userName + "考勤" + checkInOut.getCheckTime());
//                    }
//                }
//            }
//            StringBuffer sb = new StringBuffer();
//            for (UserInfo userInfo : alreadyUser) {
//                //  if (alreadyUser.get(userInfo.getName()) != null) {
//                if (checkInOutMap.get(userInfo.getName()) == null) {
//                    sb.append(userInfo.getUserId() + " " + userInfo.getName() + "还没有考勤" + date + " " + begin + ":00 " + date + " " + end + ":00");
//                    System.out.println(userInfo.getUserId() + " " + userInfo.getName() + "还没有考勤" + date + " " + begin + ":00 " + date + " " + end + ":00");
//                    //  logger.info(userInfo.getUserId() + " " + userInfo.getName() + "还没有考勤"+date + " " + begin + ":00 "+date + " " + end + ":00");
//                }
//                // }
//            }
//            //EmailUtil.send("371452875@qq.com", sb.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//    }

//    public List<UserInfo> getUserListFromAccess() {
//        Statement stmt = null;
//        ResultSet rs = null;
//        String tableName = null;
//        try {
//            tableName = "CHECKINOUT";
//            stmt = (Statement) conn.createStatement();
//            // 读取第一个表的内容
//            rs = stmt.executeQuery("select * from USERINFO ");
//            ResultSetMetaData data = rs.getMetaData();
//            List<UserInfo> list = new ArrayList<UserInfo>();
//            while (rs.next()) {
//                //for (int i = 1; i <= data.getColumnCount(); i++) {
//                UserInfo userInfo = new UserInfo();
//                userInfo.setUserId(rs.getString("USERID").trim());
//                userInfo.setCode(rs.getString("Badgenumber").trim());
//                userInfo.setName(rs.getString("Name").trim());
//
//                System.out.print(rs.getString("Badgenumber").trim() + " " + rs.getString("Name").trim());
//                list.add(userInfo);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

//    public List<UserInfo> getUserListFromExcel() throws IOException {
//        File file = PathManager.getInstance().getClassPath().resolve("user.xls").toFile();
//        List<Map<String, String>> list = ExcelUtil.getExcelData(file);
//        List<UserInfo> userList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, String> item = list.get(i);
//            //  String code = MapUtils.getString(item, "员工编号").trim().substring(1);
//            String name = MapUtils.getString(item, "姓名").trim();
//            String telno = MapUtils.getString(item, "手机").trim();
//            String email = MapUtils.getString(item, "邮箱").trim();
//            UserInfo user = new UserInfo();
//            user.setCode(MapUtils.getString(item, "员工编号").trim());
//            //  user.setCode(Integer.valueOf(code) + "");
//            user.setName(name);
//
//            user.setTelno(telno);
//            user.setEmail(email);
//            userList.add(user);
//        }
//        return userList;
//    }

    /*public HashMap<String, UserInfo> getUserMapFromExcel() throws IOException {
        List<Map<String, String>> list = ExcelUtil.getExcelData(new File("g://20180615WiFi运营中心通讯录.xlsx"));
        List<UserInfo> userList = new ArrayList<>();
        HashMap<String, UserInfo> name2beanUserMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> item = list.get(i);
            String code = MapUtils.getString(item, "员工编号").trim().substring(1);
            String name = MapUtils.getString(item, "姓名").trim();
            String telno = MapUtils.getString(item, "手机").trim();
            String email = MapUtils.getString(item, "邮箱").trim();
            UserInfo user = new UserInfo();
            user.setCode(Integer.valueOf(code) + "");
            user.setName(name);
            user.setTelno(telno);
            user.setEmail(email);
            userList.add(user);
            name2beanUserMap.put(name, user);
        }
        return name2beanUserMap;
    }*/

//    public static final String YYYY_MM_DD = "yyyy-MM-dd";

//    public static String getTodayDate() {
//        return formatToString(new Date(), YYYY_MM_DD);
//    }

    /**
     * 根据指定格式转换成日期字符串
     */
//    public static String formatToString(Date date, String format) {
//        if (date == null) {
//            return "";
//        }
//        DateFormat df = new SimpleDateFormat(format);
//        return df.format(date);
//    }
}

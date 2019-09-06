package com.dozenx;


import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.ExcelUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 12:30 2018/10/17
 * @Modified By:
 */
public class ReadAccess2 {
    static final  Logger logger = LoggerFactory.getLogger(ReadAccess2.class);
    Connection conn;

    public void init(File mdbFile) throws Exception {
        Properties prop = new Properties();
        prop.put("charSet", "gb2312"); // 这里是解决中文乱码
        prop.put("user", "");
        prop.put("password", "");
        // String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
        //   + mdbFile.getAbsolutePath();
        String url = "jdbc:ucanaccess://" + mdbFile.getAbsolutePath();
        Statement stmt = null;
        ResultSet rs = null;
        String tableName = null;
        // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            // 连接到mdb文件
            conn = DriverManager.getConnection(url, prop);
        } catch (Exception e) {
            logger.error("连接数据库失败", e);
            throw e;
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    /**
     * TODO : 读取文件access
     *
     * @return
     * @throws ClassNotFoundException
     */
    public List<CheckInOut> getCheckInOuts(String begin, String end) {
        Statement stmt = null;
        ResultSet rs = null;
        String tableName = "CHECKINOUT";
        try {
            stmt = (Statement) conn.createStatement();
            String sql = "select CHECKINOUT.USERID ,CHECKINOUT.CHECKTIME ,USERINFO.Name  from CHECKINOUT  CHECKINOUT  left join USERINFO   USERINFO on CHECKINOUT.USERID = USERINFO.USERID" +
                    "  where CHECKINOUT.CHECKTIME between #"+begin+"# and #"+end+"# ";
            logger.info(sql);
            rs = stmt.executeQuery(sql);


            // 读取第一个表的内容
          //  rs = stmt.executeQuery("select * from USERINFO ");

            // 读取第一个表的内容
            //stmt.setDate(1, new java.sql.Date(parseToDate(begin, "yyyy-MM-dd HH:mm:ss").getTime()));
            //stmt.setDate(2, new java.sql.Date(parseToDate(end, "yyyy-MM-dd HH:mm:ss").getTime()));
           // rs = stmt.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            List<CheckInOut> list = new ArrayList<CheckInOut>();

            while (rs.next()) {
                // for (int i = 1; i <= data.getColumnCount(); i++) {
                CheckInOut checkInOut = new CheckInOut();
                checkInOut.setUserId(rs.getString("USERID"));
                checkInOut.setCheckTime(rs.getString("CHECKTIME"));
                checkInOut.setName(rs.getString("Name"));
                list.add(checkInOut);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseToDate(String s, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            // e.printStackTrace();
            //System.err.println(s + "日期格式不对");
            return null;
        }
    }


    //"2018/10/17 06:00:00", "2018/10/17 08:30:59"
    //"2018/10/17 06:00:00", "2018/10/17 08:30:59"

//    public static void main1(String[] args) {
//
//        String timeStr = "08:28-06:00-08:31,12:18-11:50-12:21,01:50-01:20-01:51,18:00-17:30-18:01";
//        ReadAccess readAccess = new ReadAccess();
//        try {
//            // readAccess.init(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));//开始连接数据库
//
//            readAccess.init(new File("C:\\Program Files (x86)\\ZKTeco\\ATT2000.MDB"));//开始连接数据库
//            String timeStrAry[] = timeStr.split(",");
//
//
//            Calendar calendar = Calendar.getInstance();
//            while (true) {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//
//
//                String begin = null;
//                String end = null;
//
//                for (int i = 0; i < timeStrAry.length; i++) {
//                    String hourAndMin[] = timeStrAry[i].split("-")[0].split(":");
//                    if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {
//                        begin = timeStrAry[i].split("-")[1];
//                        end = timeStrAry[i].split("-")[2];
//                        break;
//                    } else {
//
//
//                    }
//                }
//                if (begin == null) {
//                    continue;
//                }
//                //查找用户信息
//                List<UserInfo> userInfoList = readAccess.getUserListFromAccess();
//
//
//                String dateStr = getTodayDate();
//                List<CheckInOut> checkInOuts = readAccess.getCheckInOuts(dateStr + " " + begin + ":00", dateStr + " " + end + ":00");
//                //用户早上8:30分考勤记录是否存在
//                String userName = "王作品,张智威,郝旭,张霄,李旭锟";
//                HashMap alreadyUser = new HashMap();
//                String[] userNames = userName.split(",");
//                for (String un : userNames) {
//                    alreadyUser.put(un, un);
//                }
//                HashMap<String, String> checkInOutMap = new HashMap<String, String>();
//                for (int i = 0; i < checkInOuts.size(); i++) {
//                    CheckInOut checkInOut = checkInOuts.get(i);
//                    logger.info(checkInOut.getUserId()+" "+checkInOut.getCheckTime());
//                    checkInOutMap.put(checkInOut.getUserId().trim(), checkInOut.getCheckTime());
//
//                }
//                for (UserInfo userInfo : userInfoList) {
//                    //  if (alreadyUser.get(userInfo.getName()) != null) {
//                    if (checkInOutMap.get(userInfo.getUserId()) == null) {
//                        logger.info(userInfo.getUserId()+" "+userInfo.getName() + "还没有考勤");
//                    }
//                    // }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//    }

    public static void main(String[] args) {
        //   String timeStr = "08:28-06:00-08:30,12:18-11:50-12:20,01:50-01:20-01:50,18:00-17:30-18:00";
        String propertiesPath = PathManager.getInstance().getClassPath().resolve("properties/config.properties").toString();
        logger.info("properties file path"+propertiesPath);
        PropertiesUtil.load(propertiesPath);
        ReadAccess2 readAccess = new ReadAccess2();
        try {
            // readAccess.init(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));//开始连接数据库

            readAccess.init(new File(PropertiesUtil.get("access.path")));//开始连接数据库
            //      String timeStrAry[] = timeStr.split(",");


            Calendar calendar = Calendar.getInstance();


            String begin = "06:00";
            String end = "08:30";


            //查找用户信息 这一步从access 读取出用户的姓名和id 的列表数据
            List<UserInfo> usersFromAccess = readAccess.getUserListFromAccess();
            //这一步从excel 读取出用户的基本信息数据 主要以姓名为主键 然后从access的数据里提取出userid
            List<UserInfo> alreadyUser = readAccess.getUserListFromExcel();
            //姓名对应的id
            HashMap<String, String> name2idMap = new HashMap();
            for (UserInfo userInfo : usersFromAccess) {
                name2idMap.put(userInfo.getName(), userInfo.getUserId());
            }


            //id对应的用户信息
            HashMap<String, UserInfo> id2UserMap = new HashMap();
            //excel的数据再补充userid 从数据库中
            for (UserInfo userInfo : alreadyUser) {
                userInfo.setUserId(name2idMap.get(userInfo.getName()));//补冲userid数据
                id2UserMap.put(userInfo.getUserId(), userInfo);
            }


            //查询整个事件段的考勤数据
            String dateStr = DateUtil.toDateStr(new Date(),"yyyy/MM/dd");
            List<CheckInOut> checkInOuts = readAccess.getCheckInOuts(dateStr + " " + begin + ":00", dateStr + " " + end + ":00");
            //用户早上8:30分考勤记录是否存在
            HashMap<String, String> checkInOutMap = new HashMap<String, String>();
            for (int i = 0; i < checkInOuts.size(); i++) {
                CheckInOut checkInOut = checkInOuts.get(i);
                logger.info(checkInOut.getUserId()+" "+checkInOut.getCheckTime());
                checkInOutMap.put(checkInOut.getUserId().trim(), checkInOut.getCheckTime());
            }
            for (UserInfo userInfo : alreadyUser) {
                //  if (alreadyUser.get(userInfo.getName()) != null) {
                if (checkInOutMap.get(userInfo.getUserId()) == null) {
                    System.out.println(userInfo.getUserId()+" "+userInfo.getName() + "还没有考勤");
                    logger.info(userInfo.getUserId()+" "+userInfo.getName() + "还没有考勤");
                }
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
    }

    public List<UserInfo> getUserListFromAccess() {
        Statement stmt = null;
        ResultSet rs = null;
        String tableName = null;
        try {
            tableName = "CHECKINOUT";
            stmt = (Statement) conn.createStatement();
            // 读取第一个表的内容
            rs = stmt.executeQuery("select * from USERINFO ");
            ResultSetMetaData data = rs.getMetaData();
            List<UserInfo> list = new ArrayList<UserInfo>();
            while (rs.next()) {
                //for (int i = 1; i <= data.getColumnCount(); i++) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(rs.getString("USERID").trim());
                userInfo.setCode(rs.getString("Badgenumber").trim());
                userInfo.setName(rs.getString("Name").trim());

                System.out.print(rs.getString("Badgenumber").trim() + " " + rs.getString("Name").trim());
                list.add(userInfo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<UserInfo> getUserListFromExcel() throws IOException {
        File file = PathManager.getInstance().getClassPath().resolve("user.xls").toFile();
        List<Map<String, String>> list = ExcelUtil.getExcelData(file);
        List<UserInfo> userList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> item = list.get(i);
          //  String code = MapUtils.getString(item, "员工编号").trim().substring(1);
            String name = MapUtils.getString(item, "姓名").trim();
         //   String telno = MapUtils.getString(item, "手机").trim();
          //  String email = MapUtils.getString(item, "邮箱").trim();
            UserInfo user = new UserInfo();
          //  user.setCode(Integer.valueOf(code) + "");
            user.setName(name);
           // user.setTelno(telno);
          //  user.setEmail(email);
            userList.add(user);
        }
        return userList;
    }

    public HashMap<String, UserInfo> getUserMapFromExcel() throws IOException {
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
    }

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String getTodayDate() {
        return formatToString(new Date(), YYYY_MM_DD);
    }

    /**
     * 根据指定格式转换成日期字符串
     */
    public static String formatToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}

package com.dozenx.service;

import com.dozenx.CheckInOut;
import com.dozenx.UserInfo;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:03 2018/11/6
 * @Modified By:
 */
public class CheckinOutService {
    static final Logger logger = LoggerFactory.getLogger(CheckinOutService.class);
    private Connection conn;

    public Connection getConn() {
        return this.conn;
    }

    /**
     * 连接access数据库 获取conn 连接
     *
     * @param mdbFile
     * @throws Exception
     */
    public void initConnection2Access(File mdbFile) throws Exception {
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

    public List<CheckInOut> getCheckInOuts(String begin, String end) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String tableName = "CHECKINOUT";
        try {
            stmt = conn.prepareStatement("select * from CHECKINOUT  where CHECKTIME between ? and ? ");
            // 读取第一个表的内容
            stmt.setDate(1, new java.sql.Date(DateUtil.parseToDate(begin, "yyyy-MM-dd HH:mm:ss").getTime()));
            stmt.setDate(2, new java.sql.Date(DateUtil.parseToDate(end, "yyyy-MM-dd HH:mm:ss").getTime()));
            rs = stmt.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            List<CheckInOut> list = new ArrayList<CheckInOut>();

            while (rs.next()) {
                // for (int i = 1; i <= data.getColumnCount(); i++) {
                CheckInOut checkInOut = new CheckInOut();
                checkInOut.setUserId(rs.getString("USERID"));
                checkInOut.setCheckTime(rs.getString("CHECKTIME").substring(0, 19));
                checkInOut.setCheckType(rs.getString("CHECKTYPE").equals("I") ? 1 : 0);
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


    public List<CheckInOut> getCheckInOuts(Long begin, Long end) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String tableName = "CHECKINOUT";
        try {
            stmt = conn.prepareStatement("select * from CHECKINOUT  where CHECKTIME between ? and ? ");
            // 读取第一个表的内容
            stmt.setDate(1, new java.sql.Date(begin));
            stmt.setDate(2, new java.sql.Date(end));
            rs = stmt.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            List<CheckInOut> list = new ArrayList<CheckInOut>();

            while (rs.next()) {
                // for (int i = 1; i <= data.getColumnCount(); i++) {
                CheckInOut checkInOut = new CheckInOut();
                checkInOut.setUserId(rs.getString("USERID"));
                checkInOut.setCheckTime(rs.getString("CHECKTIME").substring(0, 19));
                checkInOut.setCheckType(rs.getString("CHECKTYPE").equals("I") ? 1 : 0);
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

               // System.out.print(rs.getString("Badgenumber").trim() + " " + rs.getString("Name").trim());
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
            String telno = MapUtils.getString(item, "手机").trim();
            String email = MapUtils.getString(item, "邮箱").trim();
            UserInfo user = new UserInfo();
            user.setCode(MapUtils.getString(item, "员工编号").trim());
            //  user.setCode(Integer.valueOf(code) + "");
            user.setName(name);

            user.setTelno(telno);
            user.setEmail(email);
            userList.add(user);
        }
        return userList;
    }


   public List<UserInfo> alreadyUser = null;
    public HashMap<String, String> id2NameMap = new HashMap();

    public void initUser() throws IOException {
        //查找用户信息 这一步从access 读取出用户的姓名和id 的列表数据
        List<UserInfo> usersFromAccess = this.getUserListFromAccess();
        List<HashMap<String, String>> list = new ArrayList<>();
        for (UserInfo userInfo : usersFromAccess) {
            HashMap<String, String> map = new HashMap<>();
            if (StringUtil.isBlank(userInfo.getUserId()))
                continue;
            map.put("outId", userInfo.getUserId());
            map.put("username", userInfo.getName());
            list.add(map);
        }
        //这一步从excel 读取出用户的基本信息数据 主要以姓名为主键 然后从access的数据里提取出userid
       // alreadyUser = this.getUserListFromExcel();
        //姓名对应的id
      /*  HashMap<String, String> name2idMap = new HashMap();


        for (UserInfo userInfo : usersFromAccess) {
            name2idMap.put(userInfo.getName(), userInfo.getUserId());
            id2NameMap.put(userInfo.getUserId(), userInfo.getName());
        }


        //id对应的用户信息
        HashMap<String, UserInfo> id2UserMap = new HashMap();
        List<HashMap<String, String>> list = new ArrayList<>();
        //excel的数据再补充userid 从数据库中
        for (UserInfo userInfo : alreadyUser) {
            userInfo.setUserId(name2idMap.get(userInfo.getName()));//补冲userid数据
            id2UserMap.put(userInfo.getUserId(), userInfo);
            HashMap<String, String> map = new HashMap<>();
            if (StringUtil.isBlank(userInfo.getUserId()))
                continue;
            map.put("outId", userInfo.getUserId());
            map.put("username", userInfo.getName());
            map.put("telno", userInfo.getTelno());
            map.put("email", userInfo.getEmail());
            map.put("account", userInfo.getEmail());
            map.put("remark", userInfo.getCode());
            list.add(map);
        }
*/
        //将用户端的用户推送给服务器端
        try {
            HashMap map = new HashMap();
            map.put("params", JsonUtil.toJson(list));
            HttpRequestUtil.sendPost(PropertiesUtil.get("synuser.url"), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dozenx.web.module.calendar.bean;

/**
 * Created by dozen.zhang on 2017/3/29.
 */
/**
 * @Title: Activity.cfg
 * @Package cola.machine.calendar.activity.bean
 * @Description: 日历的事件安排
 * @author: 371452875@qq.com
 * @date: 2014年4月9日 22:03:34
 * @version: V1.0
 */


public class Activity {
    public static String BUSYLEVEL_BUSY="1";
    public static String BUSYLEVEL_IDLE="0";

    public boolean isdel;
    public boolean isIsdel() {
        return isdel;
    }
    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    /**
     * @Fields title :活动标题
     */
    private String title;

    //主键id

    public Long getId() {
        return id;
    }

    /**
     * @Fields activityId : 主键id
     */
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }
    //开始时间
    /**
     * @Fields startTime : 开始时间 YYYY-MM-DD H24:MI:SS
     */
    private long startTime;
    //结束时间
    /**
     * @Fields endTime :结束时间 YYYY-MM-DD H24:MI:SS
     */
    private long endTime;
    //地点
    /**
     * @Fields address :发生的地点
     */
    private String address;
    //对应的userid
    /**
     * @Fields userId : 用户的id
     */
    private Long userId;
    //说明
    /**
     * @Fields description : 详细描述
     */
    private String description;
    private String userName;
    //活动自定义类型
    /**
     * @Fields type : 活动归属类型 工作 学习 生活
     */
    private byte type;
    //活动的性质 0默认 1公开 2不公开
    /**
     * @Fields privacy :  0默认 1公开 2不公开
     */
    private byte privacy;
    //状态显示 有空 忙碌
    /**
     * @Fields busyLevel : 状态显示 0:有空 1:忙碌
     */
    private String busyLevel; //0有空 1忙碌


    /**
     * 获取开始时间
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     * @param startTime
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    /**
     * 设置结束时间
     * @return
     */
    public long getEndTime() {
        return endTime;
    }
    /**
     * 设置结束时间
     * @param endTime
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    /**
     * 获得活动地址
     * @return
     */
    public String getAddress() {
        return address;
    }
    /**
     * 设置活动地址
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 获得用户id
     * @return
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 设置用户id
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    /**
     * 得到描述信息
     * @return
     */
    public String getDescription() {
        return description;
    }
    /**
     * 描述信息set
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * 获得类型 工作 学习
     * @return
     */
    public byte getType() {
        return type;
    }
    /**
     * 设置类型 工作 学习
     * @param type
     */
    public void setType(byte type) {
        this.type = type;
    }
    /**
     * 隐私程度
     * @return
     */
    public byte getPrivacy() {
        return privacy;
    }
    /**
     * 隐私程度 set
     * @return
     */
    public void setPrivacy(byte privacy) {
        this.privacy = privacy;
    }
    /**
     * 忙碌程度 BUSYLEVEL_IDLE 0 空闲 BUSYLEVEL_BUSY 忙碌  1
     * @return
     */
    public String getBusyLevel() {
        return busyLevel;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**

     * 设置忙碌程度 BUSYLEVEL_IDLE 0 空闲 BUSYLEVEL_BUSY 忙碌  1
     * @param busyLevel
     */
    public void setBusyLevel(String busyLevel) {
        this.busyLevel = busyLevel;
    }

    /**
     * 获得标题
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题信息
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}


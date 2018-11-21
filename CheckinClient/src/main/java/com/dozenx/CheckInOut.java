package com.dozenx;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:00 2018/10/17
 * @Modified By:
 */
public class CheckInOut {
    String userId;
    String checkTime;
    String Name;
    int CheckType;

    public int getCheckType() {
        return CheckType;
    }

    public void setCheckType(int checkType) {
        CheckType = checkType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}

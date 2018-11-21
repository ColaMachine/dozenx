package com.dozenx.web.module.goods.action;

import com.dozenx.web.core.auth.session.SessionUser;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:26 2018/10/31
 * @Modified By:
 */
public class ChatMsg {

    public ChatMsg(ChatSessionUser user,String roomId,String msg){
        this.from = user;
        this.msg =msg;
        this.roomId =roomId;
    }
    SessionUser from;
    String roomId;
    SessionUser to;
    String msg;
    long time;
    String timeStr;

    public SessionUser getFrom() {
        return from;
    }

    public void setFrom(SessionUser from) {
        this.from = from;
    }

    public SessionUser getTo() {
        return to;
    }

    public void setTo(SessionUser to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}

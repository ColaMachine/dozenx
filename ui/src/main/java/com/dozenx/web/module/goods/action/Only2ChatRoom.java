/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-24 23:32:59
 * 文件说明:
 */

package com.dozenx.web.module.goods.action;

import com.dozenx.web.core.auth.session.SessionUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Only2ChatRoom {
    int status =0;
    public Only2ChatRoom(ChatSessionUser user,ChatSessionUser another){
        this.a=user;
        this.b=another;
        this.a.chatRoom=this;
        this.b.chatRoom=this;

        this.beginTime = System.currentTimeMillis();
        this.beginC = Calendar.getInstance();
        this.begin = this.beginC.getTime();

    }


    String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    Long beginTime;
    Long endTime;
    Calendar beginC;
    Calendar endC;
    Date begin;
    Date end;
    ChatSessionUser a;
    ChatSessionUser b;
    List<ChatMsg> msgs=new ArrayList<>();

    public void sendMsg(ChatSessionUser user,String msg){
        msgs.add(new ChatMsg( user,this.getRoomId(),user.getUserName()+":"+msg));

    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Calendar getBeginC() {
        return beginC;
    }

    public void setBeginC(Calendar beginC) {
        this.beginC = beginC;
    }

    public Calendar getEndC() {
        return endC;
    }

    public void setEndC(Calendar endC) {
        this.endC = endC;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ChatSessionUser getA() {
        return a;
    }

    public void setA(ChatSessionUser a) {
        this.a = a;
    }

    public ChatSessionUser getB() {
        return b;
    }

    public void setB(ChatSessionUser b) {
        this.b = b;
    }
}

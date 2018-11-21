/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-24 23:32:59
 * 文件说明:
 */

package com.dozenx.web.module.goods.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatRegistry {
    static final HashMap<Long, Only2ChatRoom> roomMap = new HashMap<>();
    //创建者的id 对应的房间
    static final Queue<Only2ChatRoom> wattingRoomList = new ConcurrentLinkedQueue<>();
    static final Queue<ChatSessionUser> WAITTINGPEOPLE = new LinkedBlockingQueue<>();
    static final HashMap<Long, ChatSessionUser> sessionUserMap = new HashMap<>();

    public static void addNewRoom(Only2ChatRoom room){
        if(room.a.getUserId()!=null ){
            wattingRoomList.add(room);
            roomMap.put(room.a.getUserId(),room);
        }


    }

    public static void removeUser(ChatSessionUser user){
        //TODO增加 移除失效用户的逻辑

    }

    public static ChatSessionUser getSessionUser(Long userId){
        return sessionUserMap.get(userId);
    }
}
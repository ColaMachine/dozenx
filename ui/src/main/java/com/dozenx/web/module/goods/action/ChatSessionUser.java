package com.dozenx.web.module.goods.action;

import com.dozenx.web.core.auth.session.SessionUser;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:45 2018/10/31
 * @Modified By:
 */
public class ChatSessionUser extends SessionUser {
    public ChatStatus status = ChatStatus.begin;
    public boolean isActive(){
        return true;//TODO 判断用户是否在先
    }
    Only2ChatRoom chatRoom;

    public Only2ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(Only2ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}

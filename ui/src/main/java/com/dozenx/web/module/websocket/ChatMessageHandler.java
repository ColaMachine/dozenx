package com.dozenx.web.module.websocket;

import java.io.IOException;
import java.util.ArrayList;

import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.session.SessionUser;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatMessageHandler extends TextWebSocketHandler {

    private static final ArrayList<WebSocketSession> users;// 这个会出现性能问题，最好用Map来存储，key用userid
    private static Logger logger = Logger.getLogger(ChatMessageHandler.class);

    static {
        users = new ArrayList<WebSocketSession>();
    }

    /**
     * 连接成功时候，会触发UI上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        users.add(session);
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        // TextMessage returnMessage = new TextMessage("你将收到的离线");
        // session.sendMessage(returnMessage);
    }

    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SessionUser from =   (SessionUser)(session.getAttributes().get(Constants.SESSION_USER));
        String to ="大家";
       ;

        sendMessageToUsers(new TextMessage(from.getUserName()+"对 "+to+" 说:"+new String (message.asBytes())));
        //super.handleTextMessage(session, message);
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(/*WebSocketSession session ,*/String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Constants.SESSION_USER).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {

                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
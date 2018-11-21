package com.dozenx.web.module.websocket;

import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.session.SessionUser;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:48 2018/10/30
 * @Modified By:
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        String userName ="";
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session !=null) { //使用userName区分WebSocketHandler，以便定向发送消息
                 SessionUser sessionUser =
                (SessionUser) session.getAttribute(Constants.SESSION_USER);
                if(sessionUser == null) {
                    return false;

                }
                attributes.put(Constants.SESSION_USER, sessionUser);

            }else{
                return false;
            }
        }

        //使用userName区分WebSocketHandler，以便定向发送消息(使用shiro获取session,或是使用上面的方式)
        //  String userName = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER);
//        if (userName == null) {
//            userName = "default-system";
//        }
        attributes.put(Constants.SESSION_USER, StringUtil.getRandomString(5));

        boolean flag =  super.beforeHandshake(request, response, wsHandler, attributes);
        return flag;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}

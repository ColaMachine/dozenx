package com.dozenx.web.module.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:47 2018/10/30
 * @Modified By:
 */
//@EnableWebMvc
//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatMessageHandler(),"/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor());

        registry.addHandler(chatMessageHandler2(),"/webSocketServer2").addInterceptors(new WebSocketHandshakeInterceptor());


        registry.addHandler(chatMessageHandler(), "/sockjs/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
    }

    @Bean
    public TextWebSocketHandler chatMessageHandler(){
        return new ChatMessageHandler();
    }


    @Bean
    public TextWebSocketHandler chatMessageHandler2(){
        return new ChatMessageHandler2();
    }

}
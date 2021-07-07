package ar.edu.unq.reviewitbackend.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import ar.edu.unq.reviewitbackend.config.handler.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
    
    @Autowired
    private ChatWebSocketHandler handler;
    
    public void setChatWebSocketHandler(ChatWebSocketHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/chat").setAllowedOriginPatterns("*");
        registry.addHandler(handler, "/chat").withSockJS();
    }
    
}

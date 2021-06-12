package ar.edu.unq.reviewitbackend.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import ar.edu.unq.reviewitbackend.config.handler.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{

    private final static String CHAT_ENDPOIN = "/chat";
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHanflerRegistry) {
        webSocketHanflerRegistry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOIN)
            .setAllowedOriginPatterns("*");
        
    }
    @Bean
    public WebSocketHandler getChatWebSocketHandler(){
        return new ChatWebSocketHandler();
    }
    
}

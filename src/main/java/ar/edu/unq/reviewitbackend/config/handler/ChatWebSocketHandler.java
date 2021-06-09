package ar.edu.unq.reviewitbackend.config.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.websocket.WsSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class ChatWebSocketHandler extends TextWebSocketHandler{

    private final List<WebSocketSession> webSOcketSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        webSOcketSessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception{
        for(WebSocketSession wSSession: webSOcketSessions){
            wSSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        webSOcketSessions.remove(session);
    }
}

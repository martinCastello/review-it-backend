package ar.edu.unq.reviewitbackend.config.handler;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import ar.edu.unq.reviewitbackend.entities.Messages;
import ar.edu.unq.reviewitbackend.services.UserService;


public class ChatWebSocketHandler extends TextWebSocketHandler{

    private final List<WebSocketSession> webSOcketSessions = new ArrayList<>();
    @Autowired
	protected UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        webSOcketSessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception{
        for(WebSocketSession wSSession: webSOcketSessions){
            ObjectMapper mapper = new ObjectMapper();
            Messages messageParse = mapper.readValue(message.getPayload().toString(), Messages.class);
            wSSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        webSOcketSessions.remove(session);
    }
}

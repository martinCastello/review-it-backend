package ar.edu.unq.reviewitbackend.config.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.services.MessageService;


public class ChatWebSocketHandler extends TextWebSocketHandler{

    private final List<WebSocketSession> webSOcketSessions = new ArrayList<>();
    @Autowired
	protected MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        webSOcketSessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Message messageParse = mapper.readValue(message.getPayload().toString(), Message.class);
        this.messageService.save(messageParse);
        for(WebSocketSession wSSession: webSOcketSessions){
            wSSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        webSOcketSessions.remove(session);
    }
}

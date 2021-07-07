package ar.edu.unq.reviewitbackend.config.handler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.services.MessageService;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    private final ConcurrentMap<Pair<Long, Long>, String> allClients = new ConcurrentHashMap<>();
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final List<WebSocketSession> webSOcketSessions = new ArrayList<>();

    @Autowired
	protected MessageService messageService;
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
    	webSOcketSessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String m = message.getPayload();
        LOGGER.info("handleTextMessage: {}", m);
        ObjectMapper mapper = new ObjectMapper();
        Message messageParse = mapper.readValue(m.toString(), Message.class);
        if(messageParse.getMessage().equals("openSession")) {
        	allClients.put(Pair.with(messageParse.getIdFrom(), messageParse.getIdTo()), session.getId());
        }else {
        	this.messageService.save(messageParse);
            session.sendMessage(new TextMessage(m.getBytes(UTF8)));
            for(WebSocketSession wSSession: webSOcketSessions){
                if(wSSession.getId().equals(allClients.get(Pair.with(messageParse.getIdTo(), messageParse.getIdFrom()))))
                	wSSession.sendMessage(message);
            }
        }
        LOGGER.info("allClients - count: {}", allClients.size());
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.info("handleTransportError", exception);
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
    	allClients.entrySet().removeIf(entry -> (session.getId().equals(entry.getValue())));
    	webSOcketSessions.remove(session);
    }
    
}

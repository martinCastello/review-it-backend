package ar.edu.unq.reviewitbackend.services;

import java.util.List;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;

public interface MessageService {
    List<Message> findAllByTo(User user);
    
    List<Message> findAllByFrom(User user);

    List<Message> findAll(String userFrom, String userTo);

	Message save(Message message);

	void delete(Message message);

}

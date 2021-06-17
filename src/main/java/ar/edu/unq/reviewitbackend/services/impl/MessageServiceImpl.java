package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.MessageRepository;
import ar.edu.unq.reviewitbackend.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
	private MessageRepository repository;

    @Override
    public List<Message> findAllByTo(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Override
    public List<Message> findAllByFrom(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Override
    public Message save(Message message) {
        return this.repository.save(message);
    }
    
}

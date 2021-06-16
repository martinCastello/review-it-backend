package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.reviewitbackend.entities.Messages;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.MessageRepository;
import ar.edu.unq.reviewitbackend.services.MessageService;


public class MessageServiceImpl implements MessageService {

    @Autowired
	private MessageRepository repository;

    @Override
    public List<Messages> findAllByTo(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Override
    public List<Messages> findAllByFrom(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Override
    public Messages save(Messages message) {
        return this.repository.save(message);
    }
    
}

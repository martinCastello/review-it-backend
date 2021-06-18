package ar.edu.unq.reviewitbackend.services.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.MessageRepository;
import ar.edu.unq.reviewitbackend.services.MessageService;
import ar.edu.unq.reviewitbackend.services.UserService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
	private MessageRepository repository;
    
    @Autowired
    private UserService userService;

    @Override
    public List<Message> findAllByTo(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Override
    public List<Message> findAllByFrom(User user) {
        return this.repository.findAllByFrom(user);
    }

    @Transactional
    public Message save(Message message) {
    	User from = this.userService.findById(message.getIdFrom()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		User to = this.userService.findById(message.getIdTo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		message.setFrom(from);
		message.setTo(to);
		Date date = new Date();
		message.setCreatedDate(date);
		message.setLastModifiedDate(date);
        return this.repository.save(message);
    }
    
}

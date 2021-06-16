package ar.edu.unq.reviewitbackend.services;

import java.util.List;

import ar.edu.unq.reviewitbackend.entities.Messages;
import ar.edu.unq.reviewitbackend.entities.User;

public interface MessageService {
    List<Messages> findAllByTo(User user);
    
    List<Messages> findAllByFrom(User user);

	Messages save(Messages message);

}

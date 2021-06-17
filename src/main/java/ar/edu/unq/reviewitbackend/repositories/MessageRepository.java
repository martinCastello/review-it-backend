package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.pk.MessagePK;

@Repository
public interface MessageRepository extends JpaRepository<Message, MessagePK>{
    List<Message> findAllByTo(User user);
	
	List<Message> findAllByFrom(User user);
}

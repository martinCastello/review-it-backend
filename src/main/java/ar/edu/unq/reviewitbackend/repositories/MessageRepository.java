package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.reviewitbackend.entities.Messages;
import ar.edu.unq.reviewitbackend.entities.User;

public interface MessageRepository extends JpaRepository<Messages, Long>{
    List<Messages> findAllByTo(User user);
	
	List<Messages> findAllByFrom(User user);
}

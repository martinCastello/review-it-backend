package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findAllByTo(User user);
	
    List<Message> findAllByFrom(User user);
	@Query("SELECT m FROM message WHERE (m.from_user_fk = 2 AND m.to_user_fk = 1) OR (m.to_user_fk = 2 AND m.from_user_fk= 1) ORDER BY m.id ASC")
	List<Message> findByFromAndTo(User userFrom, User userTo);
}

package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findAllByTo(User user);
	
    List<Message> findAllByFrom(User user);
    
 	@Query("SELECT m FROM Message m WHERE (m.from = (:userFrom) AND m.to = (:userTo)) OR (m.to = 2 AND m.from= 1) ORDER BY m.id ASC")
	List<Message> findByFromAndTo(@Param("userFrom") User userFrom, @Param("userTo") User userTo);
}

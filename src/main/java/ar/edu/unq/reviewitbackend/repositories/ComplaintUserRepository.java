package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.enums.ComplaintReason;

@Repository
public interface ComplaintUserRepository extends JpaRepository<ComplaintUser, Long> {

	Optional<ComplaintUser> findByUserAndToAndReason(User user, User to, ComplaintReason reason);

	List<ComplaintUser> findByUserAndTo(User user, User to);

}

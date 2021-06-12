package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.pk.FollowersPK;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowersPK>{

	Page<Follower> findAllByTo(User user, Pageable pageable);
	
	Page<Follower> findAllByFrom(User user, Pageable pageable);

	List<Follower> findAllByFrom(User user);

	Optional<Follower> findByFromAndTo(User from, User to);

}

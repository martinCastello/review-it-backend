package ar.edu.unq.reviewitbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.User;

public interface FollowerService {

    Page<Follower> findAllByTo(User user, Pageable pageable);
    
    Page<Follower> findAllByFrom(User user, Pageable pageable);

	Follower save(Follower followRelation);

	List<Follower> findAllByFrom(User user);

	void delete(Follower requestFollow);

	Optional<Follower> findByFromAndTo(User from, User to);
}

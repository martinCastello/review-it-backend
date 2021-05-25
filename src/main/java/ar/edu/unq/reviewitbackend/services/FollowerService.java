package ar.edu.unq.reviewitbackend.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;

public interface FollowerService {

    Page<Followers> findAllByTo(User user, Pageable pageable);
    
    List<Followers> findAllByFrom(User user);

	Followers save(Followers followRelation);
}

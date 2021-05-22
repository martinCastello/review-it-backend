package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;

public interface FollowerService {

    Page<Followers> findAllByTo(User user, Pageable pageable);

	Followers save(Followers followRelation);
}

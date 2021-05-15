package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;

public interface FollowerService  extends CommonService<Followers>{

    public Page<Followers> findAllByTo(User to, Pageable pageable);
}

package ar.edu.unq.reviewitbackend.services.impl;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.FollowerRepository;
import ar.edu.unq.reviewitbackend.services.FollowerService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImp extends CommonServiceImpl<Followers, FollowerRepository> implements FollowerService{
    public Page<Followers> findAllByTo(User to, Pageable pageable) {
		return this.repository.findAllByTo(to, pageable);
	}
}

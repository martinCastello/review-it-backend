package ar.edu.unq.reviewitbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.FollowerRepository;
import ar.edu.unq.reviewitbackend.services.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService{
    
	@Autowired
	private FollowerRepository repository;
	
	public Page<Followers> findAllByTo(User user, Pageable pageable) {
		return this.repository.findAllByTo(user, pageable);
	}

	@Transactional
	public Followers save(Followers followRelation) {
		return this.repository.save(followRelation);
	}

	public Page<Followers> findAllByFrom(User user, Pageable pageable) {
		return this.repository.findAllByFrom(user, pageable);
	}

}

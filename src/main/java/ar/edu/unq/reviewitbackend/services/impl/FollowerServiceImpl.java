package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.FollowerRepository;
import ar.edu.unq.reviewitbackend.services.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService{
    
	@Autowired
	private FollowerRepository repository;
	
	public Page<Follower> findAllByTo(User user, Pageable pageable) {
		return this.repository.findAllByTo(user, pageable);
	}

	@Transactional
	public Follower save(Follower followRelation) {
		return this.repository.save(followRelation);
	}

	public Page<Follower> findAllByFrom(User user, Pageable pageable) {
		return this.repository.findAllByFrom(user, pageable);
	}

	@Override
	public List<Follower> findAllByFrom(User user) {
		return this.repository.findAllByFrom(user);
	}

	@Transactional
	public void delete(Follower entity) {
		this.repository.delete(entity);
	}

	@Override
	public Optional<Follower> findByFromAndTo(User from, User to) {
		return this.repository.findByFromAndTo(from, to);
	}

}

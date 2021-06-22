package ar.edu.unq.reviewitbackend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.repositories.ComplaintReviewRepository;
import ar.edu.unq.reviewitbackend.repositories.ComplaintUserRepository;
import ar.edu.unq.reviewitbackend.services.ComplaintService;
import ar.edu.unq.reviewitbackend.services.FollowerService;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	private ComplaintReviewRepository complaintReviewRepository;
	
	@Autowired
	private ComplaintUserRepository complaintUserRepository;
	
	@Autowired
	private FollowerService followerService;
	
	@Transactional
	public ComplaintReview save(ComplaintReview entity) {
		switch(entity.getReason()) {
			case NOT_INTERESTED:
				break;
			default:
				break;
		}	
		return this.complaintReviewRepository.save(entity);
	}
	
	@Transactional
	public ComplaintUser save(ComplaintUser entity) {
		switch(entity.getReason()) {
			case NOT_INTERESTED:
				Optional<Follower> relationship = this.followerService.findByFromAndTo(entity.getUser(), entity.getTo());
				if(relationship.isPresent())
					this.followerService.delete(relationship.get());
				break;
			default:
				break;
		}
		return this.complaintUserRepository.save(entity);
	}
}

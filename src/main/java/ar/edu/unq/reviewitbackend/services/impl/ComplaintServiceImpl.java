package ar.edu.unq.reviewitbackend.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.enums.ComplaintReason;
import ar.edu.unq.reviewitbackend.entities.enums.Penalty;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;
import ar.edu.unq.reviewitbackend.repositories.ComplaintReviewRepository;
import ar.edu.unq.reviewitbackend.repositories.ComplaintUserRepository;
import ar.edu.unq.reviewitbackend.services.ComplaintService;
import ar.edu.unq.reviewitbackend.services.FollowerService;
import ar.edu.unq.reviewitbackend.services.UserService;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ComplaintServiceImpl.class);
	
	@Value("${complaint.level}")
	private Integer complaintLevel;
	
	@Autowired
	private ComplaintReviewRepository complaintReviewRepository;
	
	@Autowired
	private ComplaintUserRepository complaintUserRepository;
	
	@Autowired
	private FollowerService followerService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public ComplaintReview save(ComplaintReview entity) {
		this.userService.save(entity.getUser());
		this.userService.save(entity.getReview().getUser());
		return this.complaintReviewRepository.save(entity);
	}
	
	public ComplaintReview apply(ComplaintReview entity) {
		switch(entity.getReason()) {
			case NOT_INTERESTED:
				break;
			case SPOILER:
				this.userService.addComplaint(entity.getReview().getUser(), Penalty.SLOW.getNumber());
				break;
			case BAD_LANGUAGE:
				this.userService.addComplaint(entity.getReview().getUser(), Penalty.SLOW.getNumber());
				break;
			default:
				break;
		}
		this.userService.addBlockedReview(entity.getUser(), entity.getReview());
		this.userService.analyzeSetPenaltyDate(entity.getReview().getUser(), complaintLevel);
		return this.save(entity);
	}
	
	@Transactional
	public ComplaintUser save(ComplaintUser entity) {
		this.userService.save(entity.getUser());
		this.userService.save(entity.getTo());
		return this.complaintUserRepository.save(entity);
	}
	
	public ComplaintUser apply(ComplaintUser entity) throws ComplaintTypeException {
		if(this.existComplaint(entity.getUser(), entity.getTo(), entity.getReason())) {
			throw new ComplaintTypeException(entity.getReason().getLabel());
		}
		Optional<Follower> relationship = this.followerService.findByFromAndTo(entity.getUser(), entity.getTo());
		if(relationship.isPresent())
			this.followerService.delete(relationship.get());
		switch(entity.getReason()) {
			case NOT_INTERESTED:
				break;
			case SPOILER:
				this.userService.addComplaint(entity.getTo(), Penalty.STERN.getNumber());
				break;
			case BAD_LANGUAGE:
				this.userService.addComplaint(entity.getTo(), Penalty.MODERATE.getNumber());
				break;
			default:
				break;
		}
		if(notExistComplaint(entity.getUser(), entity.getTo())) {
			this.userService.addBlockedUser(entity.getUser(), entity.getTo());
		}
		this.userService.analyzeSetPenaltyDate(entity.getTo(), complaintLevel);
		return this.save(entity);
	}
	
	private boolean notExistComplaint(User user, User to) {
		return this.complaintUserRepository.findByUserAndTo(user, to).isEmpty();
	}

	private boolean existComplaint(User user, User to, ComplaintReason reason) {
		Optional<ComplaintUser> complaint = this.complaintUserRepository.findByUserAndToAndReason(user, to, reason);
		return complaint.isPresent() 
				&& (to.getLastPenaltyDate() == null || complaint.get().getCreatedDate().after(to.getLastPenaltyDate()));
	}

	@Override
	public void removeIfPenaltyDateIsBeforeOrNull(User user, User to) {
		for(ComplaintUser complaint : this.complaintUserRepository.findByUserAndTo(user, to)) {
			if(to.getLastPenaltyDate() == null || complaint.getCreatedDate().after(to.getLastPenaltyDate())) {
				LOGGER.info("Al usuario " + to.getUserName() + " se le quita puntaje de penalidad por " + complaint.getReason().getLabel());
				switch(complaint.getReason()) {
					case NOT_INTERESTED:
						break;
					case SPOILER:
						this.userService.addComplaint(to, -Penalty.STERN.getNumber());
						break;
					case BAD_LANGUAGE:
						this.userService.addComplaint(to, -Penalty.MODERATE.getNumber());
						break;
					default:
						break;
				}
				this.complaintUserRepository.deleteById(complaint.getId());
				this.userService.save(user);
				this.userService.save(to);
			}
		}
	}
	
}

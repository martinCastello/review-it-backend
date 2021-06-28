package ar.edu.unq.reviewitbackend.services;

import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;

public interface ComplaintService{

	ComplaintReview save(ComplaintReview entity);
	
	ComplaintUser save(ComplaintUser entity);

	void removeIfPenaltyDateIsBeforeOrNull(User user, User userToUnblock);

	ComplaintReview apply(ComplaintReview entity) throws ComplaintTypeException;
	
	ComplaintUser apply(ComplaintUser entity) throws ComplaintTypeException;

}

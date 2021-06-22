package ar.edu.unq.reviewitbackend.services;

import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.ComplaintUser;

public interface ComplaintService{

	ComplaintReview save(ComplaintReview entity);
	
	ComplaintUser save(ComplaintUser entity);

}

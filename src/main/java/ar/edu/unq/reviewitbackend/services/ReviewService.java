package ar.edu.unq.reviewitbackend.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Review;

public interface ReviewService extends CommonService<Review>{
	

	Page<Review> findAll(Long id, String description, Pageable pageable);
	
	Optional<Review> findByID(Long id);

}

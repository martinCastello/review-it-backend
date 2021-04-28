package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Review;

public interface ReviewService extends CommonService<Review>{

	Page<Review> findAllByTitle(String title, Pageable pageable);
	
	Page<Review> findAllByDescription(String description, Pageable pageable);

	Page<Review> findAllByPoints(Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescription(String title, String description, Pageable pageable);
	
	Page<Review> findAllByTitleAndPoints(String title, Integer points, Pageable pageable);
	
	Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescriptionAndPoints(String title, String description, Integer points,
			Pageable pageable);

	Page<Review> findAllBySearch(String search, Pageable pageable);

}

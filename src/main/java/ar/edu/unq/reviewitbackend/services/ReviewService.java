package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Review;

public interface ReviewService extends CommonService<Review>{

	Page<Review> findAllByDescription(String description, Pageable pageable);

	Page<Review> findAllByPoints(Integer points, Pageable pageable);

	Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

}

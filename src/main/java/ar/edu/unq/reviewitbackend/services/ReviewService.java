package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.Review;
import javassist.NotFoundException;

public interface ReviewService extends CommonService<Review>{

	Page<Review> findAll(String inAll, String title, String description, Integer points, String userName,Long userId, Pageable pageable);
	
	Page<Review> findAllByTitle(String title, Pageable pageable);
	
	Page<Review> findAllByDescription(String description, Pageable pageable);

	Page<Review> findAllByPoints(Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescription(String title, String description, Pageable pageable);
	
	Page<Review> findAllByTitleAndPoints(String title, Integer points, Pageable pageable);
	
	Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescriptionAndPoints(String title, String description, Integer points,
			Pageable pageable);

	Page<Review> findAllBySearch(String search, Pageable pageable);

	Review create(Review entity);

	Commentary createCommentary(Commentary entity) throws NotFoundException;

	Page<Commentary> findAllCommetariesById(Long id, Pageable pageable) throws NotFoundException;

}

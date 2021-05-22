package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.Review;

public interface CommentaryService extends CommonService<Commentary>{

	Page<Commentary> findAllByReview(Review review, Pageable pageable);

}

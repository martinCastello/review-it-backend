package ar.edu.unq.reviewitbackend.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.repositories.CommentaryRepository;
import ar.edu.unq.reviewitbackend.services.CommentaryService;

@Service
public class CommentaryServiceImpl extends CommonServiceImpl<Commentary, CommentaryRepository> implements CommentaryService{

	@Override
	public Page<Commentary> findAllByReview(Review review, Pageable pageable) {
		return this.repository.findAllByReview(review, pageable);
	}

}

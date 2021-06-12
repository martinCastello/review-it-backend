package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.LikeRepository;
import ar.edu.unq.reviewitbackend.services.LikeService;

@Service
public class LikeServiceImpl extends CommonServiceImpl<Likes, LikeRepository> implements LikeService{

	@Override
	public Optional<Likes> findByReviewAndUser(Review review, User user) {
		return this.repository.findByReviewAndUser(review, user);
	}

	@Override
	public void delete(Likes like) {
		this.repository.delete(like);
	}

	@Override
	public List<Likes> findByReview(Review review) {
		return this.repository.findAllByReview(review);
	}

}

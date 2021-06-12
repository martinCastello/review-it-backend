package ar.edu.unq.reviewitbackend.services;

import java.util.List;
import java.util.Optional;

import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;

public interface LikeService extends CommonService<Likes>{

	Optional<Likes> findByReviewAndUser(Review review, User user);

	void delete(Likes like);

	List<Likes> findByReview(Review review);

}

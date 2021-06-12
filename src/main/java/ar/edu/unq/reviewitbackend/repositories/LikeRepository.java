package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long>{

	Optional<Likes> findByReviewAndUser(Review review, User user);

	List<Likes> findAllByReview(Review review);

}

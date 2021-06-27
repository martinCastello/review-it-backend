package ar.edu.unq.reviewitbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.enums.ComplaintReason;

@Repository
public interface ComplaintReviewRepository extends JpaRepository<ComplaintReview, Long> {

	Optional<User> findByUserAndReviewAndReason(User user, Review review, ComplaintReason reason);
}

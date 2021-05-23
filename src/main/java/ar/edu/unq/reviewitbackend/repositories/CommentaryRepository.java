package ar.edu.unq.reviewitbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.Review;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long>{

	Page<Commentary> findAllByReview(Review review, Pageable pageable);

}

package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("select new ar.edu.unq.reviewitbackend.dto.DropdownInfo( e.id ) FROM Review e")
	List<DropdownInfo> findDropdownInfo();

	Page<Review> findAllByTitle(String title, Pageable pageable);
	
	Page<Review> findAllByDescription(String description, Pageable pageable);

	Page<Review> findAllByPoints(Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescription(String title, String description, Pageable pageable);
	
	Page<Review> findAllByTitleAndPoints(String title, Integer points, Pageable pageable);
	
	Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

	Page<Review> findAllByTitleAndDescriptionAndPoints(String title, String description, Integer points,
			Pageable pageable);

	Page<Review> findAllByTitleOrDescriptionOrPointsOrUser(String title, String description, Integer points, User userId,
			Pageable pageable);

	Page<Review> findAllByTitleContains(String title, Pageable pageable);

	List<Review> findAllByUser(User user);
  
	@Query("SELECT r FROM Review r LEFT JOIN User u ON r.user = u WHERE u.isPrivate = 0 OR u.id IN :ids ORDER BY u.isPrivate DESC, r.id DESC")
	Page<Review> listOfReviewOfUsers(@Param("ids")List<Long> userIds, Pageable pageable);

	Optional<Review> findByTitleAndUser(String title, User user);

}

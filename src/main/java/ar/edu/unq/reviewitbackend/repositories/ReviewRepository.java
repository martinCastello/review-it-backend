package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Review;

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

	Page<Review> findAllByTitleOrDescriptionOrPointsOrUserId(String search, String search2, Integer search3, Long search4,
			Pageable pageable);

}

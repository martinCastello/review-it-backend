package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("select new ar.edu.unq.reviewitbackend.dto.DropdownInfo( e.id ) FROM Review e")
	List<DropdownInfo> findDropdownInfo();

}

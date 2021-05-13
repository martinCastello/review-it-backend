package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface FollowerRepository extends JpaRepository<Followers, Long>{

	@Query("select new ar.edu.unq.reviewitbackend.dto.DropdownInfo( e.id ) FROM Followers e")
	List<DropdownInfo> findDropdownInfo();

	Page<Review> findAllByTo(User to, Pageable pageable);
	

}

package ar.edu.unq.reviewitbackend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("select new ar.edu.unq.reviewitbackend.dto.DropdownInfo( u.id ) FROM User u")
	List<DropdownInfo> findDropdownInfo();

	Page<User> findAllByDescription(String description, Pageable pageable);

	Page<User> findAllByPoints(Integer points, Pageable pageable);

	Page<User> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

}

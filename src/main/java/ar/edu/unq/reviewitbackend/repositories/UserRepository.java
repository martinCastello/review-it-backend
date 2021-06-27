package ar.edu.unq.reviewitbackend.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	Page<User> findAllByName(String name, Pageable pageable);

	Page<User> findAllByLastName(String lastName, Pageable pageable);
	
	Page<User> findAllByEmail(String email, Pageable pageable);
	
	Page<User> findAllByUserName(String userName, Pageable pageable);

	Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable);

	Optional<User> findByUserNameOrEmail(String userName, String email);

	Optional<User> findByUserNameAndEmail(String userName, String email);

	Optional<User> findByUserName(String userName);

	List<User> findByNameContainsOrLastNameContains(String name, String lastName);

	List<User> findByNameContains(String nameOrLastName);

	List<User> findByNameContainsAndLastNameContains(String name, String lastName);

	List<User> findByBlockedIsTrueAndLastPenaltyDateBefore(Date lastUpdated);

}

package ar.edu.unq.reviewitbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.entities.pk.FollowersPK;

@Repository
public interface FollowerRepository extends JpaRepository<Followers, FollowersPK>{

	Page<Followers> findAllByTo(User user, Pageable pageable);
	

}

package ar.edu.unq.reviewitbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Commentary;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long>{

}

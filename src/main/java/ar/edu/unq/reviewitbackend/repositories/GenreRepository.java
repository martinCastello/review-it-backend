package ar.edu.unq.reviewitbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.reviewitbackend.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{

}

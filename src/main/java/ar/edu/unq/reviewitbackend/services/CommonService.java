package ar.edu.unq.reviewitbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;

public interface CommonService<E> {
	
	Page<E> findAll(Pageable pageable);
	
	Optional<E> findById(Long id);
	
	E save(E entity);
	
	boolean existEntity(Long id);

	List<DropdownInfo> findDropdownInfo();

}

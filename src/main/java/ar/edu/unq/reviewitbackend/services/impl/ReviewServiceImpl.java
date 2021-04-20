package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;
import ar.edu.unq.reviewitbackend.services.ReviewService;

@Service
public class ReviewServiceImpl extends CommonServiceImpl<Review, ReviewRepository> implements ReviewService{

	public Page<Review> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Page<Review> findAllByDescription(String description, Pageable pageable) {
		return this.repository.findAllByDescription(description, pageable);
	}
	
	public Page<Review> findAllByPoints(Integer points, Pageable pageable) {
		return this.repository.findAllByPoints(points, pageable);
	}

	public Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable) {
		return this.repository.findAllByDescriptionAndPoints(description, points, pageable);
	}
	
	public List<DropdownInfo> findDropdownInfo() {
        return this.repository.findDropdownInfo();
    }
	
}

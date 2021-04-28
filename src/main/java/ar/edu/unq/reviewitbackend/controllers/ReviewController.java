package ar.edu.unq.reviewitbackend.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/reviews")
public class ReviewController extends CommonController<Review, ReviewService> {

	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "points", required = false) Integer points) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(title != null && title.length() > 0) {
			if (description != null && description.length() > 0) {
	    		if(points != null) {
	    			return ResponseEntity.ok(this.service.findAllByTitleAndDescriptionAndPoints(title, description, points, pageRequest));
	    		}
	    		return ResponseEntity.ok(this.service.findAllByTitleAndDescription(title, description, pageRequest));
	    	}
	    	if(points != null)
	    		return ResponseEntity.ok(this.service.findAllByTitleAndPoints(title, points, pageRequest));
	        return ResponseEntity.ok(this.service.findAllByTitle(title, pageRequest));
		}else if (description != null && description.length() > 0) {
    		if(points != null) {
    			return ResponseEntity.ok(this.service.findAllByDescriptionAndPoints(description, points, pageRequest));
    		}
    		return ResponseEntity.ok(this.service.findAllByDescription(description, pageRequest));
    	}
    	if(points != null)
    		return ResponseEntity.ok(this.service.findAllByPoints(points, pageRequest));
        return ResponseEntity.ok(this.service.findAll(pageRequest));
	}

	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdateReview(@RequestBody Review entity) {
		Review oEntity = service.save(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
}
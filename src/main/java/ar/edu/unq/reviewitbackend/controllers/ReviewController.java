package ar.edu.unq.reviewitbackend.controllers;

import javax.persistence.PostUpdate;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/review")
public class ReviewController extends CommonController<Review, ReviewService> {

	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination, @RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "description", required = false) String description) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(pageRequest));
	}

	@PostMapping("/save")
	@CrossOrigin(origins = "*")
	public ResponseEntity<Review> createOrUpdateReview(@RequestBody Review reviewBody) {
		service.save(reviewBody);
		return new ResponseEntity<Review>(reviewBody, HttpStatus.OK);
	}
}
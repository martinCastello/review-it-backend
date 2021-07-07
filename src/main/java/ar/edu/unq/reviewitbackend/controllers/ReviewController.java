package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.exceptions.BlockedUserException;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;
import ar.edu.unq.reviewitbackend.exceptions.ReviewExistException;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import javassist.NotFoundException;

@RestController
@RequestMapping(path="/reviews")
public class ReviewController extends CommonController<Review, ReviewService> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

	@GetMapping("/{owner}")
	public ResponseEntity<?> getAllBy(Pagination pagination,
			@RequestParam(value = "search", required = false) String inAll,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "genre", required = false) String genre,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "points", required = false) Integer points,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "userName", required = false) String userName,
			@PathVariable String owner) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		LOGGER.info("Usuarioa a realizar la busqueda de reseñas: " + owner);
		try {
			return ResponseEntity.ok(this.service.findAll(inAll, title, genre, description, points, name, userName, owner, pageRequest));
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}

	@GetMapping("/getForUser/{userName}")
	public ResponseEntity<?> getAllForUser(Pagination pagination, @PathVariable String userName) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		try {
			return ResponseEntity.ok(this.service.findReviewsForUser(userName, pageRequest));
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Review entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		try{
			Review oEntity = service.create(entity);
			return ResponseEntity.ok(oEntity);
		}catch(NotFoundException | ReviewExistException | BlockedUserException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<?> modify(@Valid @RequestBody Review entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		try{
			Review oEntity = service.modify(entity);
			return ResponseEntity.ok(oEntity);
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getAllErrors().forEach(err -> {
				errores.put(((FieldError) err).getField(), err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@PostMapping("/createCommentary")
	public ResponseEntity<?> createCommentary(@Valid @RequestBody Commentary entity, BindingResult result) throws NotFoundException {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Commentary oEntity = service.createCommentary(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	@GetMapping("/{id}/comments")
	public ResponseEntity<?> getComments(Pagination pagination, @PathVariable Long id) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		try {
			return ResponseEntity.ok(this.service.findAllCommetariesById(id, pageRequest));
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
        	this.service.deleteById(id);
        	return ResponseEntity.ok().build();
        }catch(Exception e) {
        	return ResponseEntity.badRequest().build();
        }
    }
	
	@PostMapping("/likear")
	public ResponseEntity<?> like(@Valid @RequestBody Likes entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		try{
			Likes oEntity = service.like(entity);
			return ResponseEntity.ok(oEntity);
		}catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}/likes")
	public ResponseEntity<?> getLikes(@PathVariable Long id) {
		try{
			return ResponseEntity.ok(this.service.getLikes(id));
		}catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/denounce")
	public ResponseEntity<?> denounce(@Valid @RequestBody ComplaintReview entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		try {
			ComplaintReview oEntity = service.denounce(entity);
			return ResponseEntity.ok(oEntity);
		}catch(NotFoundException | ComplaintTypeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
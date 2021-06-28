package ar.edu.unq.reviewitbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.ComplaintReview;
import ar.edu.unq.reviewitbackend.entities.Genre;
import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;
import ar.edu.unq.reviewitbackend.exceptions.ReviewExistException;
import ar.edu.unq.reviewitbackend.repositories.GenreRepository;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;
import ar.edu.unq.reviewitbackend.services.CommentaryService;
import ar.edu.unq.reviewitbackend.services.ComplaintService;
import ar.edu.unq.reviewitbackend.services.LikeService;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.OrderBy;
import javassist.NotFoundException;

@Service
public class ReviewServiceImpl extends CommonServiceImpl<Review, ReviewRepository> implements ReviewService{

	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentaryService commentaryService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private ComplaintService complaintService;
	
	public Page<Review> findAll(String inAll, String title, String genre, String description, Integer points, String nameOrLastName, String userName, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<Review> root = cr.from(Review.class);
		List<Predicate> predicatesAnd = new ArrayList<>();
		List<Predicate> predicatesOr = new ArrayList<>();
		if(title != null) {
			predicatesAnd.add(cb.like(root.get("title"), '%'+title+'%'));
		}
		if(description != null) {
			predicatesAnd.add(cb.like(root.get("description"), '%'+description+'%'));
		}
		if(genre != null) {
			predicatesAnd.add(cb.like(root.get("genres").as(String.class) , '%'+genre+'%'));
		}
		if(points != null) {
			predicatesAnd.add(cb.equal(root.get("points"), points));
		}
		if(nameOrLastName != null && nameOrLastName.length() > 0) {
			String[] nameAndLastName = nameOrLastName.split(" ");
			List<User> users = new ArrayList<>();
			if(nameAndLastName.length == 2) {
				users.addAll(this.userService.findByNameContainsAndLastNameContains(nameAndLastName[0], nameAndLastName[1]));
				users.addAll(this.userService.findByNameContainsAndLastNameContains(nameAndLastName[1], nameAndLastName[0]));
			}else {
				for(String dataUser : nameAndLastName) {
					for(User user : this.userService.findByNameContainsOrLastNameContains(dataUser, dataUser)) {
						if(!users.stream().map(u -> u.getUserName()).collect(Collectors.toList()).contains(user.getUserName()))
							users.add(user);
					}
				}
			}
			In<User> inClause = cb.in(root.get("user"));
			for (User user : users) {
			    inClause.value(user);
			}
			predicatesAnd.add(inClause);
		}
		if(userName != null && userName.length() > 0){
			Optional<User> oUser = this.userService.findByUserName(userName);
			predicatesAnd.add(cb.equal(root.get("user"), oUser.get()));
		}
		if(inAll != null) {
			predicatesOr.add(cb.like(root.get("title"), '%'+inAll+'%'));
			predicatesOr.add(cb.like(root.get("genres").as(String.class), '%'+inAll+'%'));
			predicatesOr.add(cb.like(root.get("description"), '%'+inAll+'%'));
			try{
				Integer.valueOf(inAll);
				predicatesOr.add(cb.equal(root.get("points"), inAll));
			}catch (Exception e) {
				predicatesOr.add(cb.equal(root.get("points"), 0));
			}
			Optional<User> oUser = this.userService.findByUserName(inAll);
			if(oUser.isPresent())
				predicatesOr.add(cb.equal(root.get("user"), oUser.get()));
		}
		List<Predicate> predicates = new ArrayList<>();
		if(!predicatesAnd.isEmpty())
			predicates.add(cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()])));
		if(!predicatesOr.isEmpty())
			predicates.add(cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()])));
		cr.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		cr.select(cb.count(root));
		long total = em.createQuery(cr).getSingleResult();

		CriteriaBuilder cb2 = em.getCriteriaBuilder();
		CriteriaQuery<Review> cr2 = cb2.createQuery(Review.class);
		Root<Review> root2 = cr2.from(Review.class);
		cr2.where(cb2.and(predicates.toArray(new Predicate[predicates.size()])));

		List<OrderBy> orderByList = new ArrayList<>();
		pageable.getSort().get().forEach(s -> orderByList.add(new OrderBy(s.getProperty(), s.getDirection().toString())));
		List<Order> orders = this.buildOrder(orderByList, cb2, root2);
		cr2.orderBy(orders).select(root2);
		List<Review> content = em.createQuery(cr2).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
		em.close();
		return new PageImpl<>(content, pageable, total);
	}

	public Page<Review> findAllBySearch(String search, Pageable pageable){
		int searchNumber = 0;
		Optional<User> oUser;
		try{
			searchNumber = Integer.valueOf(search);
		}catch (Exception e) {
			searchNumber = 0;
		}
		oUser = userService.findByUserName(search);
		return this.repository.findAllByTitleOrDescriptionOrPointsOrUser(search, search, Integer.valueOf(searchNumber), oUser.orElse(null), pageable);
		
	}
	
	public Page<Review> findAllByTitle(String title, Pageable pageable) {
		return this.repository.findAllByTitleContains(title, pageable);
	}

	public Page<Review> findAllByDescription(String description, Pageable pageable) {
		return this.repository.findAllByDescription(description, pageable);
	}
	
	public Page<Review> findAllByPoints(Integer points, Pageable pageable) {
		return this.repository.findAllByPoints(points, pageable);
	}

	public Page<Review> findAllByTitleAndDescription(String title, String description, Pageable pageable) {
		return this.repository.findAllByTitleAndDescription(title, description, pageable);
	}

	public Page<Review> findAllByTitleAndPoints(String title, Integer points, Pageable pageable) {
		return this.repository.findAllByTitleAndPoints(title, points, pageable);
	}
	
	public Page<Review> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable) {
		return this.repository.findAllByDescriptionAndPoints(description, points, pageable);
	}

	public Page<Review> findAllByTitleAndDescriptionAndPoints(String title, String description, Integer points,
			Pageable pageable) {
		return this.repository.findAllByTitleAndDescriptionAndPoints(title, description, points, pageable);
	}
	
	public List<DropdownInfo> findDropdownInfo() {
        return this.repository.findDropdownInfo();
    }

	@Override
	public Review create(Review entity) throws ReviewExistException, NotFoundException {
		User user = this.userService.findById(entity.getUserId()).orElseThrow(() -> new NotFoundException("No se encuentra un usuario con ese id")); 
		this.repository.findByTitleAndUser(entity.getTitle(), user).orElseThrow(() -> new ReviewExistException(entity.getTitle()));
		List<Genre> genres = this.genreRepository.findAllById(entity.getGenresId());
		List<String> genresDescription = genres.stream().map(genre->genre.getName()).collect(Collectors.toList());
		entity.setGenres(genresDescription);
		entity.setUser(user);
		return this.save(entity);
	}

	@Override
	public Commentary createCommentary(Commentary entity) throws NotFoundException {
		Review review = this.findById(entity.getReviewId()).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		User user = this.userService.findById(entity.getUserId()).orElseThrow(() -> new NotFoundException("Usuario no encontrada"));
		entity.setReview(review);
		entity.setUser(user);
		return this.commentaryService.save(entity);
	}
	
	public Page<Commentary> findAllCommetariesById(Long id, Pageable pageable) throws NotFoundException{
		Review review = this.findById(id).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		return this.commentaryService.findAllByReview(review, pageable);
	}

	@Override
	public void deleteById(Long id) throws NotFoundException {
		Review entity = this.findById(id).orElseThrow(() -> new NotFoundException("La reseña que pretende eliminar no se encuentra"));
        this.repository.delete(entity);
	}

	@Override
	public Likes like(Likes entity) throws NotFoundException {
		Review review = this.findById(entity.getReviewId()).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		User user = this.userService.findById(entity.getUserId()).orElseThrow(() -> new NotFoundException("Usuario no encontrada"));
		Optional<Likes> oEntity = this.likeService.findByReviewAndUser(review, user);
		if(oEntity.isEmpty()) {
			entity.setReview(review);
			entity.setUser(user);
			return this.likeService.save(entity);
		}else {
			this.likeService.delete(oEntity.get());
			return null;
		}
	}

	@Override
	public List<Likes> getLikes(Long id) throws NotFoundException {
		Review review = this.findById(id).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		return this.likeService.findByReview(review);
	}

	@Override
	public List<Review> findAllByUser(User user) {
		return this.repository.findAllByUser(user);
	}

	@Override
	public Review modify(Review entity) throws NotFoundException {
		Review review = this.findById(entity.getId()).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		review.setDescription(entity.getDescription());
		review.setPoints(entity.getPoints());
		return this.save(review);
	}

	@Override
	public ComplaintReview denounce(ComplaintReview entity) throws NotFoundException, ComplaintTypeException {
		Review review = this.findById(entity.getReviewId()).orElseThrow(() -> new NotFoundException("Reseña no encontrada"));
		User complainant = this.userService.findById(entity.getUserId()).orElseThrow(() -> new NotFoundException("Usuario no encontrada"));
		entity.setReview(review);
		entity.setUser(complainant);
		entity.setReason(entity.getReason());
		entity.setComment(entity.getComment());
		return this.complaintService.apply(entity);
	}
	
}

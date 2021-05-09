package ar.edu.unq.reviewitbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
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
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.OrderBy;

@Service
public class ReviewServiceImpl extends CommonServiceImpl<Review, ReviewRepository> implements ReviewService{

	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserService userService;
	
	public Page<Review> findAll(String inAll, String title, String description, Integer points, String userName, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<Review> root = cr.from(Review.class);
		List<Predicate> predicatesAnd = new ArrayList<>();
		List<Predicate> predicatesOr = new ArrayList<>();
		if(title != null) {
			predicatesAnd.add(cb.like(root.get("title"), '%'+title.toLowerCase()+'%'));
		}
		if(description != null) {
			predicatesAnd.add(cb.like(root.get("description"), '%'+description.toLowerCase()+'%'));
		}
		if(points != null) {
			predicatesAnd.add(cb.equal(root.get("points"), points));
		}
		if(userName != null && userName.length() > 0) {
			Optional<User> oUser = this.userService.findByUserName(userName);
			if(oUser.isPresent())
				predicatesAnd.add(cb.equal(root.get("user"), oUser.get()));
		}
		if(inAll != null) {
			predicatesOr.add(cb.like(root.get("title"), '%'+inAll.toLowerCase()+'%'));
			predicatesOr.add(cb.like(root.get("description"), '%'+inAll.toLowerCase()+'%'));
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
	
}

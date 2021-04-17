package ar.edu.unq.reviewitbackend.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;
import ar.edu.unq.reviewitbackend.services.ReviewService;

@Service
public class ReviewServiceImpl extends CommonServiceImpl<Review, ReviewRepository> implements ReviewService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public Page<Review> findAll(Long id, String description, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Review> cr = cb.createQuery(Review.class);
		Root<Review> root = cr.from(Review.class);
		List<Predicate> predicates = new ArrayList<>();
		if(id != null) {
			predicates.add(cb.equal(root.get("id"), id));
		}
		if(description != null) {
			predicates.add(cb.equal(root.get("description"), description));
		}
		if (predicates.isEmpty()) {
			cr.select(root);
		} else {
			cr.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		}
		TypedQuery<Review> query = em.createQuery(cr);
		long total = query.getResultList().stream().count();
		
		CriteriaBuilder cb2 = em.getCriteriaBuilder();
		CriteriaQuery<Review> cr2 = cb2.createQuery(Review.class);
		Root<Review> root2 = cr2.from(Review.class);
		if (predicates.isEmpty()) {
			cr2.select(root2);
		} else {
			cr2.select(root2).where(predicates.toArray(new Predicate[predicates.size()]));
		}
		TypedQuery<Review> query2 = em.createQuery(cr2);
		query2.setFirstResult((int) pageable.getOffset());
		query2.setMaxResults(pageable.getPageSize());
		List<Review> content = query2.getResultList();
		
		if(content.isEmpty())
			return Page.empty();
		else {
			if (pageable.isUnpaged() || pageable.getOffset() == 0) {

				if (pageable.isUnpaged() || pageable.getPageSize() > content.size()) {
					return new PageImpl<>(content, pageable, content.size());
				}

				return new PageImpl<>(content, pageable, total);
			}

			if (content.size() != 0 && pageable.getPageSize() > content.size()) {
				return new PageImpl<>(content, pageable, pageable.getOffset() + content.size());
			}

			return new PageImpl<>(content, pageable, total);
		}
	}
	
	public List<DropdownInfo> findDropdownInfo() {
        return this.repository.findDropdownInfo();
    }
}

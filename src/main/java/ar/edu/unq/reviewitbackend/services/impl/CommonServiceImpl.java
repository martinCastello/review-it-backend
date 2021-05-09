package ar.edu.unq.reviewitbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.services.CommonService;
import ar.edu.unq.reviewitbackend.utils.OrderBy;

public class CommonServiceImpl<E, R extends JpaRepository<E, Long>> implements CommonService<E> {
	
	@Autowired
	protected R repository;
	
	public Page<E> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}
	
	public Optional<E> findById(Long id) {
		return this.repository.findById(id);
	}
	
	public boolean existEntity(Long id) {
		return this.findById(id).isPresent();
	}
	
	@Transactional
	public E save(E entity) {
		return this.repository.save(entity);
	}
	
	@Transactional
	public Iterable<E> saveAll(Iterable<E> entities) {
		return this.repository.saveAll(entities);
	}

	protected List<Order> buildOrder(List<OrderBy> orderByList, CriteriaBuilder builder, Root<?> root) {
		List<Order> orders = new ArrayList<>();
		for (OrderBy order : orderByList) {
			if (order.getDir().equalsIgnoreCase("desc")) {
				orders.add(builder.desc(root.get(order.getName())));
			} else {
				orders.add(builder.asc(root.get(order.getName())));
			}
		}
		return orders;
	}
	
	public List<DropdownInfo> findDropdownInfo() {
		return null;
	}	

}

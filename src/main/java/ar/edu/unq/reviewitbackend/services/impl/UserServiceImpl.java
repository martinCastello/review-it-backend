package ar.edu.unq.reviewitbackend.services.impl;

import java.io.IOException;
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
import org.springframework.util.StringUtils;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.UserRepository;
import ar.edu.unq.reviewitbackend.services.FollowerService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.OrderBy;
import javassist.NotFoundException;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, UserRepository> implements UserService{

	@Autowired
	private FollowerService followerService;

	@Autowired
	private EntityManager em;
	
	public Page<User> findAll(String inAll, String mail, String username, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<User> root = cr.from(User.class);
		List<Predicate> predicatesAnd = new ArrayList<>();
		List<Predicate> predicatesOr = new ArrayList<>();
		if(mail != null) {
			predicatesAnd.add(cb.like(root.get("email"), '%'+mail.toLowerCase()+'%'));
		}
		if(username != null) {
			predicatesAnd.add(cb.like(root.get("userName"), '%'+username.toLowerCase()+'%'));
		}
		if(inAll != null) {
			predicatesOr.add(cb.like(root.get("email"), '%'+inAll.toLowerCase()+'%'));
			predicatesOr.add(cb.like(root.get("userName"), '%'+inAll.toLowerCase()+'%'));
			predicatesOr.add(cb.like(root.get("name"), '%'+inAll.toLowerCase()+'%'));
			predicatesOr.add(cb.like(root.get("lastName"), '%'+inAll.toLowerCase()+'%'));
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
		CriteriaQuery<User> cr2 = cb2.createQuery(User.class);
		Root<User> root2 = cr2.from(User.class);
		cr2.where(cb2.and(predicates.toArray(new Predicate[predicates.size()])));

		List<OrderBy> orderByList = new ArrayList<>();
		pageable.getSort().get().forEach(s -> orderByList.add(new OrderBy(s.getProperty(), s.getDirection().toString())));
		List<Order> orders = this.buildOrder(orderByList, cb2, root2);
		cr2.orderBy(orders).select(root2);
		List<User> content = em.createQuery(cr2).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
		em.close();
		return new PageImpl<>(content, pageable, total);
	}

	public Page<User> findAllByName(String name, Pageable pageable) {
		return this.repository.findAllByName(name, pageable);
	}
	
	public Page<User> findAllByLastName(String lastName, Pageable pageable) {
		return this.repository.findAllByLastName(lastName, pageable);
	}
	
	public Page<User> findAllByEmail(String email, Pageable pageable) {
		return this.repository.findAllByEmail(email, pageable);
	}
	
	public Page<User> findAllByUserName(String userName, Pageable pageable) {
		return this.repository.findAllByUserName(userName, pageable);
	}

	public Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable) {
		return this.repository.findAllByNameAndLastName(name, lastName, pageable);
	}
	
	public List<DropdownInfo> findDropdownInfo() {
        return this.repository.findDropdownInfo();
    }

	public Boolean exist(String userName, String email) {
		return this.repository.findByUserNameOrEmail(userName, email).isPresent();
	}

	public Optional<User> findByUserNameAndEmail(String userName, String email) {
		return this.repository.findByUserNameAndEmail(userName, email);
	}

	public Optional<User> findByUserName(String userName) {
		return this.repository.findByUserName(userName);
	}

	@Override
	public Followers createRelationship(Followers requestFollow) {
		User from = this.findById(requestFollow.getIdFrom()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		User to = this.findById(requestFollow.getIdTo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		Followers followRelation = new Followers(from, to);
		return this.followerService.save(followRelation);
	}

	@Override
	public Page<Followers> findFollowersById(Long id, Pageable pageable) throws NotFoundException {
		User user = this.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.followerService.findAllByTo(user, pageable);
	}

	@Override
	public List<User> findByNameContainsOrLastNameContains(String name, String lastName) {
		return this.repository.findByNameContainsOrLastNameContains(name, lastName);
	}

	@Override
	public List<User> findByNameContains(String nameOrLastName) {
		return this.repository.findByNameContains(nameOrLastName);
	}
	
	@Override
	public Page<Followers> findFollowingsById(Long id, Pageable pageable) throws NotFoundException{
		User user = this.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.followerService.findAllByFrom(user, pageable);
	}

	@Override
	public User modify(User entity) throws NotFoundException {
		User user = this.findByUserName(entity.getUserName()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		
		String fileName = StringUtils.cleanPath(entity.getAvatarFileForView().getOriginalFilename());
		try {
            if(fileName.contains("..")) {
                throw new RuntimeException("Perdon! El nombre del archivo contiene secuencia de ruta invalida " + fileName);
            }
            user.setAvatarFile(entity.getAvatarFileForView().getBytes());
            user.setAvatar(entity.getAvatar());
    		user.setEmail(entity.getEmail());
    		user.setName(entity.getName());
    		user.setPassword(entity.getPassword());
    		return this.save(user);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". Por favor intente nuevamente!", ex);
        }
	}
	
}

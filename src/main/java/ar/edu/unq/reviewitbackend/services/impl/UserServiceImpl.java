package ar.edu.unq.reviewitbackend.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;
import ar.edu.unq.reviewitbackend.repositories.UserRepository;
import ar.edu.unq.reviewitbackend.services.ComplaintService;
import ar.edu.unq.reviewitbackend.services.FollowerService;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.OrderBy;
import javassist.NotFoundException;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, UserRepository> implements UserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Value("${complaint.level}")
	private Integer complaintLevel;
	
	@Autowired
	private FollowerService followerService;
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComplaintService complaintService;
	
	private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
	
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
	
	public User create(User user) {
		Optional<User> oUser = this.repository.findByUserName(user.getUserName());
		if(oUser.isPresent()) {
			if(oUser.get().getPassword().equals(user.getPassword())) {
				return oUser.get();
			}else {
				oUser = this.repository.findByUserName(user.getUserName()+"1");
				if(oUser.isPresent())
					return oUser.get();
				else {
					user.setUserName(user.getUserName()+"1");
					return this.save(user);
				}
			}
		}else {
			return this.save(user);
		}
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
	public Follower createRelationship(Follower requestFollow) {
		User from = this.findById(requestFollow.getIdFrom()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		User to = this.findById(requestFollow.getIdTo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		Follower followRelation = new Follower(from, to);
		this.removeBlockedUser(from, to);
		return this.followerService.save(followRelation);
	}

	private void removeBlockedUser(User user, User userToUnblock) {
		if(user.removeBlockedUser(userToUnblock)) {
			LOGGER.info("Usuario " + userToUnblock.getUserName() + " desbloqueado.");
			complaintService.removeIfPenaltyDateIsBeforeOrNull(user, userToUnblock);
		}
	}

	@Override
	public Page<Follower> findFollowersByUserName(String userName, Pageable pageable) throws NotFoundException {
		User user = this.findByUserName(userName).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
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
	public Page<Follower> findFollowingsByUserName(String userName, Pageable pageable) throws NotFoundException{
		User user = this.findByUserName(userName).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.followerService.findAllByFrom(user, pageable);
	}

	@Override
	public User modify(User entity) throws NotFoundException {
		User user = this.findByUserName(entity.getUserName()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		if(entity.getAvatarFileForView() != null) {
			String fileContentType = entity.getAvatarFileForView().getContentType();
			if(contentTypes.contains(fileContentType)) {
		    	try {
					user.setAvatarFile(entity.getAvatarFileForView().getBytes());
		        } catch (IOException ex) {
		            throw new RuntimeException("Hubo un error al procesar el archivo", ex);
		        }
		    } else {
		    	throw new RuntimeException("Solamente se permite imagenes PNG y JPG ");
		    }
		}
		user.setName(entity.getName());
		user.setLastName(entity.getLastName());
		user.setIsPrivate(entity.getIsPrivate());
		return this.save(user);
	}

	@Override
	public List<Follower> findFollowingsByUserName(String username) throws NotFoundException {
		User user = this.findByUserName(username).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.followerService.findAllByFrom(user);
	}

	@Override
	public void deleteRelationship(Follower requestFollow) {
		User from = this.findById(requestFollow.getIdFrom()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		User to = this.findById(requestFollow.getIdTo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		Follower relationship = this.followerService.findByFromAndTo(from, to).orElseThrow(() -> new RuntimeException("Relacion seguidor-seguido no encontrada"));
		this.followerService.delete(relationship);
	}
	
	public List<User> findByNameContainsAndLastNameContains(String name, String lastName) {
		return this.repository.findByNameContainsAndLastNameContains(name, lastName);
	}

	@Override
	public List<Likes> findLikesToUserName(String username) throws NotFoundException {
		User user = this.findByUserName(username).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		List<Review> reviews = this.reviewService.findAllByUser(user);
		return reviews.stream().flatMap(review -> review.getLikes().stream().filter(like -> !like.getUser().getUserName().equalsIgnoreCase(username)).collect(Collectors.toList()).stream()).collect(Collectors.toList());
	}

	@Override
	public List<Review> findReviewsByUserName(String username) throws NotFoundException {
		User user = this.findByUserName(username).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.reviewService.findAllByUser(user);
	}
	
	@Override
	public ComplaintUser denounce(ComplaintUser entity) throws NotFoundException, ComplaintTypeException {
		User complainant = this.findById(entity.getUserId()).orElseThrow(() -> new NotFoundException("Usuario denunciante no encontrado"));
		User denouncedUser = this.findById(entity.getToId()).orElseThrow(() -> new NotFoundException("Usuario denunciado no encontrado"));
		entity.setUser(complainant);
		entity.setTo(denouncedUser);
		entity.setReason(entity.getReason());
		entity.setComment(entity.getComment());
		return this.complaintService.apply(entity);
	}

	@Override
	public User addBlockedUser(User user, User userToBlock) {
		LOGGER.info("El usuario " + user.getUserName() + " agrega a su lista de bloqueados al usuario " + userToBlock.getUserName());
		return user.addBlockedUser(userToBlock);
	}

	@Override
	public void addComplaint(User user, Integer penalty) {
		user.addComplaint(penalty);
	}
	
	public void analyzeSetPenaltyDate(User user, Integer complaintCount) {
		if(!user.isBlocked() && user.getComplaintLevel() >= complaintCount) {
			Date now = new Date();
			user.setBlocked(true);
			user.setLastPenaltyDate(now);
			user.resetComplaintLevel();
		}
	}
	
	@Override
	public void addBlockedReview(User user, Review review) {
		LOGGER.info("El usuario " + user.getUserName() + " bloquea la reseña de la peli/serie " + review.getTitle() 
			+ " del usuario " + review.getUser().getUserName());
		user.addBlockedReview(review);
	}

	@Override
	public void resetComplaintCountForSchedule() {
		// Date lastUpdated = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * timeOffsetInDays);
		Date lastUpdated = new Date(new Date().getTime() - 1000 * this.timeOffsetInSeconds);
		List<User> userList = this.repository.findByBlockedIsTrueAndLastPenaltyDateBefore(lastUpdated);
		for(User user : userList) {
			user.setBlocked(false);
		}
		if(!userList.isEmpty()) {
			this.saveAll(userList);
		}
	}
	
}

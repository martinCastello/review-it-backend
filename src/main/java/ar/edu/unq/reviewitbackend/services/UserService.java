package ar.edu.unq.reviewitbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.ComplaintUser;
import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.Likes;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.exceptions.ComplaintTypeException;
import javassist.NotFoundException;

public interface UserService extends CommonService<User>{

	User create(User user);
	
	Page<User> findAll(String inAll, String mail, String userName, Pageable pageable);

	Page<User> findAllByName(String name, Pageable pageable);

	Page<User> findAllByLastName(String lastName, Pageable pageable);
	
	Page<User> findAllByEmail(String email, Pageable pageable);
	
	Page<User> findAllByUserName(String userName, Pageable pageable);

	Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable);

	Boolean exist(String userName, String email);

	Optional<User> findByUserNameAndEmail(String userName, String email);

	Optional<User> findByUserName(String search);

	Follower createRelationship(Follower requestFollow);

	Page<Follower> findFollowersByUserName(String userName, Pageable pageable) throws NotFoundException;
	
	List<User> findByNameContainsOrLastNameContains(String name, String lastName);

	List<User> findByNameContains(String nameOrLastName);

	Page<Follower> findFollowingsByUserName(String userName, Pageable pageable) throws NotFoundException;

	User modify(User entity) throws NotFoundException;

	List<Follower> findFollowingsByUserName(String username) throws NotFoundException;

	void deleteRelationship(Follower requestFollow);

	List<User> findByNameContainsAndLastNameContains(String name, String lastName);

	List<Likes> findLikesToUserName(String username) throws NotFoundException;

	List<Review> findReviewsByUserName(String username) throws NotFoundException;
	
	ComplaintUser denounce(ComplaintUser entity) throws NotFoundException, ComplaintTypeException;

	User addBlockedUser(User user, User to);

	void addComplaint(User to, Integer integer);
	
	void analyzeSetPenaltyDate(User user, Integer complaintCount);

	void addBlockedReview(User user, Review review);

	void resetComplaintCountForSchedule();
}

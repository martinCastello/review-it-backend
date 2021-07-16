package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import ar.edu.unq.reviewitbackend.entities.pk.ReviewBlockedPK;

@Entity
@Table(name="review_blocked")
@IdClass(ReviewBlockedPK.class)
public class ReviewBlocked {

	@Id
	private Long userId;

	@Id
	private Long reviewId;
	
	public ReviewBlocked() {}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
}

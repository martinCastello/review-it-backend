package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="complaint_review")
public class ComplaintReview extends Complaint{

	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Review review;
	
	@NotNull
	@Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long reviewId;
	
	public ComplaintReview() {}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	
	
}

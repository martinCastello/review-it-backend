package ar.edu.unq.reviewitbackend.entities.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ReviewBlockedPK implements Serializable{

	@NotNull
	@Column(name="user_id")
	private Long userId;

    @NotNull
    @Column(name="review_id")
    private Long reviewId;
    
    public ReviewBlockedPK() {}
    
    public ReviewBlockedPK(Long userId, Long reviewId) {
		this.userId = userId;
		this.reviewId = reviewId;
	}

}

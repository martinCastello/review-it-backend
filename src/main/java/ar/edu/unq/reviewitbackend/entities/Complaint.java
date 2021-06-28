package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.reviewitbackend.entities.enums.ComplaintReason;

@MappedSuperclass
public abstract class Complaint extends Auditable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private User user;

	@NotNull
	@Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
	
	@NotNull(message = "Debe indicar un motivo de la denuncia")
	@Enumerated(EnumType.STRING)
	private ComplaintReason reason;
	
    private String comment;

    public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ComplaintReason getReason() {
		return reason;
	}

	public void setReason(ComplaintReason reason) {
		this.reason = reason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

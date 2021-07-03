package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="complaint_user")
public class ComplaintUser extends Complaint{

	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@JsonIgnore
	private User to;
	
	@NotNull
	@Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long toId;
	
	public ComplaintUser() {}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}
	
}

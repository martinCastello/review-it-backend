package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.reviewitbackend.entities.pk.FollowersPK;
import lombok.ToString;

@ToString
@Entity
@IdClass(FollowersPK.class)
public class Follower extends Auditable {
	
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="from_user_fk")
	private User from;

	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to_user_fk")
	private User to;

	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idFrom;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idTo;
	
    public Follower() {};

    public Follower(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public User getFrom(){
        return this.from;
    }
    
    public User getTo(){
        return this.to;
        
    }

    public void setFrom(User from){
        this.from = from;
    }

    public void setTo(User to){
        this.to = to;
    }

	public Long getIdFrom() {
		return idFrom;
	}

	public void setIdFrom(Long idFrom) {
		this.idFrom = idFrom;
	}

	public Long getIdTo() {
		return idTo;
	}

	public void setIdTo(Long idTo) {
		this.idTo = idTo;
	}


}

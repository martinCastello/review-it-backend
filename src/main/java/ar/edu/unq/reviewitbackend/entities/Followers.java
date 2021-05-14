package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ar.edu.unq.reviewitbackend.entities.pk.FollowersPK;
import lombok.ToString;

@ToString
@Entity
@IdClass(FollowersPK.class)
public class Followers extends Auditable {
	
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="from_user_fk")
    private User from;

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="to_user_fk")
    private User to;
	
	@Transient
	private Long idTo;

    @Transient
	private Long idFrom;

    public Followers() {};

    public Followers(User from, User to) {
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

}

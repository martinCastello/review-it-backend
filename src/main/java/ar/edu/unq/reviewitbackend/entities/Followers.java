package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import ar.edu.unq.reviewitbackend.entities.pk.FollowersPK;
import lombok.ToString;

@ToString
@Entity
@IdClass(FollowersPK.class)
public class Followers extends Auditable {
	
	@Id
    @Column(name="from_user_fk")
	private Long from;

	@Id
	@Column(name="to_user_fk")
	private Long to;

    public Followers() {};

    public Followers(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    public Long getFrom(){
        return this.from;
    }
    
    public Long getTo(){
        return this.to;
        
    }

    public void setFrom(Long from){
        this.from = from;
    }

    public void setTo(Long to){
        this.to = to;
    }


}

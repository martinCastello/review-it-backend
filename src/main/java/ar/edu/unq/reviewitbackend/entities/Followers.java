package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
@Entity
public class Followers  extends Auditable{

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FOLLOWER")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
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

    public long getIdFrom(){
        return this.idFrom;
    }
    
    public long getIdTo(){
        return this.idTo;
        
    }

    public void setIdFrom(long idFrom){
        this.idFrom = idFrom;
    }

    public void setIdTo(long idTo){
        this.idTo = idTo;
    }

}

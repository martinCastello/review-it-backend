package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.reviewitbackend.entities.pk.MessagePK;
import lombok.ToString;

@ToString
@Entity
@IdClass(MessagePK.class)
public class Message extends Auditable{
	
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

    @NotBlank
    @Size(max = 512)
	private String message;

    @NotBlank
    @Size(max = 512)
	private String sender;
	
    public Message() {};

    public Message(User from, User to, String message, String sender) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.sender= sender;
    }

    public User getFrom(){
        return this.from;
    }
    
    public User getTo(){
        return this.to;
        
    }

    public String getMessage(){
        return this.message;
    }

    public String getSender(){
        return this.sender;
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

    public void setMessage(String message){
        this.message = message;
    }

    public void setSender(String sender){
        this.sender = sender;
    }
}

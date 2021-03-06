package ar.edu.unq.reviewitbackend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
@Entity
public class Message{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="from_user_fk")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private User from;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to_user_fk")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
    
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected Date lastModifiedDate;
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getId() {
		return id;
	}
    
    
}

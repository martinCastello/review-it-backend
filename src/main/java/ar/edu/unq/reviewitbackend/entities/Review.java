package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.ToString;

@ToString
@Entity
@SequenceGenerator(name = "SEQ_REVIEW", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_REVIEW")
public class Review extends Auditable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW")
	private Long id;
	
	@NotBlank
    @Size(max = 50)
	private String title;
	
	@NotBlank
    @Size(max = 255)
	private String description;

	private Integer points;
	
	@NotNull
	@ManyToOne
	private User user;
	
	public Review () {}
	
	public Review(String title, String description, Integer points, User user) {
		this.setDescription(description);
		this.setPoints(points);
		this.setTitle(title);
		this.setUser(user);
	}

	public Long getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Integer getPoints() {
		return this.points;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
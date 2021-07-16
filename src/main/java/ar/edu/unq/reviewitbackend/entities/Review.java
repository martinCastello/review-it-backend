package ar.edu.unq.reviewitbackend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.reviewitbackend.utils.StringListConverter;
import lombok.ToString;

@ToString
@Entity
public class Review extends Auditable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Debe completar con el titulo de la pelicula")
    @Size(max = 50)
	private String title;
	
	@NotBlank(message = "Debe dejar una opinión sobre la pelicula")
    @Size(max = 255)
	private String description;

	@NotNull(message = "Debe puntuar a la pelicula")
	private Integer points;
	
	@Convert(converter = StringListConverter.class)
	private List<String> genres;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Long> genresId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private User user;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long userId;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Commentary> commentaries;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Likes> likes;

	private String img;
	
	@Column(columnDefinition = "TEXT")
	private String overview;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComplaintReview> complaints;

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
	
	public List<Commentary> getCommentaries(){
		return this.commentaries;
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

	public Long getUserId() {
		return this.userId;
	}
	
	public void addCommentary(Commentary commentary) {
		this.commentaries.add(commentary);
	}

	public List<Likes> getLikes() {
		return likes;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<String> getGenres() {
		return genres;
	}
	
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public List<Long> getGenresId() {
		return genresId;
	}

	public void setGenresId(List<Long> genresId) {
		this.genresId = genresId;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	
}
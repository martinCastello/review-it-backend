package ar.edu.unq.reviewitbackend.entities;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
@Entity
public class Genres {
	
	@Id
	private Long id;
	
	@NotBlank(message = "Debe completar con el name de la categoria")
    @Size(max = 50)
	private String name;
	
	public Genres () {}
	
	public Genres(String name, Long id) {
		this.setName(name);
		this.setId(id);
	}

	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}
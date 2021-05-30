package ar.edu.unq.reviewitbackend.entities;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.ToString;

@ToString
@Entity
public class Genre {
	
	@Id
	private Long id;
	
	@NotBlank(message = "Debe completar con el nombre de la categoria")
    @Size(max = 50)
	private String name;
	
	public Genre () {}
	
	public Genre(String name, Long id) {
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
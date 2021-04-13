package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Review extends Auditable<String>{

	@Id
	private Long id;
	
	@NotBlank
    @Size(max = 50)
	private String title;
	
	@NotBlank
    @Size(max = 255)
	private String description;

	private Integer points;
}

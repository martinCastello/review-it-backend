package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "review", schema="public")
@SequenceGenerator(name = "SEQ_REVIEW", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_REVIEW", schema="public")
public class Review /*extends Auditable<String>*/ {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW")
	private Long id;
	
//	@NotBlank
//    @Size(max = 50)
//	private String title;
	
//	@NotBlank
//    @Size(max = 255)
//	private String description;

//	private Integer points;
	
}

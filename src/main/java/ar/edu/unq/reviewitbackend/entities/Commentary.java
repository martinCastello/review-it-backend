package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class Commentary extends Auditable{

	@NotNull
    private String message;
	
    @ManyToOne
    private Review review;
    
    @ManyToOne
    private User user;
}

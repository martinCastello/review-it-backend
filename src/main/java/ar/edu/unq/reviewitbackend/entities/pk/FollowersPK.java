package ar.edu.unq.reviewitbackend.entities.pk;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import ar.edu.unq.reviewitbackend.entities.User;

public class FollowersPK implements Serializable{

	@NotNull
	private User from;

    @NotNull
    private User to;
    
    public FollowersPK() {}
    
    public FollowersPK(User from, User to) {
		this.from = from;
		this.to = to;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

}

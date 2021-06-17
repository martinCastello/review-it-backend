package ar.edu.unq.reviewitbackend.entities.pk;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MessagePK implements Serializable{
	
	@NotNull
	private Long from;

    @NotNull
    private Long to;
    
    public MessagePK() {}
    
    public MessagePK(Long from, Long to) {
		this.from = from;
		this.to = to;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}
}

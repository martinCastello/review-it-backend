package ar.edu.unq.reviewitbackend.entities.enums;

public enum Penalty {
	SLOW(1),
	MODERATE(2),
	STERN(3);
	
	private Integer penalty;
	
	private Penalty(Integer num) {
		this.penalty = num;
	}
	
	public Integer getNumber() {
		return this.penalty;
	}
}

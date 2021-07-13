package ar.edu.unq.reviewitbackend.entities.enums;

public enum ComplaintReason {
	NOT_INTERESTED("No me interesa"),
	SPOILER("Spoiler"),
	BAD_LANGUAGE("Lenguaje inapropiado");
	
	private String label;
	
	ComplaintReason(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	
}

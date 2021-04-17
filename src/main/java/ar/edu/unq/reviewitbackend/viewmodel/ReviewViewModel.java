package ar.edu.unq.reviewitbackend.viewmodel;

public class ReviewViewModel {

	private Long id;

	private String title;

	private String body;
	
	private Integer points;

	public Long getId() {
		return this.id;
	}

	public String getBody() {
		return body;
	}
	
	public String getTitle() {
		return title;
	}

	public Integer getPoints() {
		return points;
	}

}

package ar.edu.unq.reviewitbackend.exceptions;

public class ReviewExistException  extends Exception {
    
	private static final long serialVersionUID = 1L;

	public ReviewExistException(String title) {
        super("Ya ha hecho reseña sobre esta peli/serie '" + title + "'");
    }
}

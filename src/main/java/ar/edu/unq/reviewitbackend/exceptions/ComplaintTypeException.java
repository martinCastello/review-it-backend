package ar.edu.unq.reviewitbackend.exceptions;

public class ComplaintTypeException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public ComplaintTypeException(String reason) {
        super("Ya ha hecho una denuncia de este tipo '" + reason + "'");
    }
}

package ar.edu.unq.reviewitbackend.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockedUserException extends Exception{

	private static final long serialVersionUID = 1L;

	public BlockedUserException(long miliseconds) {
		super("Usuario bloqueado hasta " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(miliseconds)));
    }
}

package web.forum.topichub.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException() {
        super(ErrorKey.CREDENTIALS.key());
    }
}

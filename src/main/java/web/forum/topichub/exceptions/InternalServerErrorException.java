package web.forum.topichub.exceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException() {
        super(ErrorKey.SERVER_ERROR.key());
    }
}

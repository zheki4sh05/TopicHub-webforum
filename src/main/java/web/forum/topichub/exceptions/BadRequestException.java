package web.forum.topichub.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super(ErrorKey.WRONG_REQUEST_PARAM.key());
    }
    public BadRequestException(String message) {
        super(message);
    }
}

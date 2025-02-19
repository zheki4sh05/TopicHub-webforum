package web.forum.topichub.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super(ErrorKey.NOT_FOUND.key());
    }
}

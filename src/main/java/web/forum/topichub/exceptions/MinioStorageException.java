package web.forum.topichub.exceptions;

public class MinioStorageException extends RuntimeException{
    public MinioStorageException(String message) {
        super(message);
    }

}

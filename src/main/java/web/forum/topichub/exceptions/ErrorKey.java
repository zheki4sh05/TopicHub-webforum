package web.forum.topichub.exceptions;

public enum ErrorKey {
    WRONG_REQUEST_PARAM ("error.wrong_request_param"),
    CONFLICT ("error.conflict"),
    EMAIL_CONFLICT ("error.conflict_email"),
    LOGIN_EMPTY("error.login_empty"),
    EMAIL_INCORRECT("error.email_incorrect"),
    CREDENTIALS("error.credentials"),
    USER_NOT_FOUND("error.user_not_found"),
    SERVER_ERROR("error.server"),
    USER_BLOCKED("error.user_block"),
    NOT_FOUND("error.not_found"),
    PASS_EMPTY("error.pass_empty"),
    IMAGE_LOAD_ERROR("error.image_load"),
    UNIQUE("error.unique"),
    PASS_INCORRECT("error.pass_incorrect");
    private final String key;

    ErrorKey(String type) {
        this.key = type;
    }

    public String key() {
        return key;
    }
}

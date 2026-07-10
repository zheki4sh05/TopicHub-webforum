package web.forum.topichub.dto.message;

public enum EmailCode {

    MSG_SBJ_PASS_RECOVERY("email.sbj.pass.recovery"),
    MSG_TITLE_PASS_RECOVERY("email.title.pass.recovery"),
    MSG_BODY_PASS_RECOVERY("email.body.pass.recovery");
    private final String key;

    EmailCode(String type) {
        this.key = type;
    }

    public String key() {
        return key;
    }
}

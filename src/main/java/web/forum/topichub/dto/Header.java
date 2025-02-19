package web.forum.topichub.dto;

public enum Header {
    NAME ("Authorization"),
    ALIAS ("Bearer ");
    private final String type;

    Header(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}

package web.forum.topichub.dto;

public enum ArticlePartType {
    PARAGRAPH("paragraph"),
    CHAPTER("chapter"),
    IMG("img"),
    IMG_LOAD("img_load");
    private final String type;

    ArticlePartType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}

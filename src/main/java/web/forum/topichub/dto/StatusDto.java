package web.forum.topichub.dto;

public enum StatusDto {
        MODERATION ("MODERATION"),
        BLOCK("BLOCK"),
        ACTIVE("ACTIVE"),
        SANDBOX("SANDBOX"),
        EDIT("EDIT"),
        PUBLISH ("PUBLISH");

        private String type;

    StatusDto(String type) {
            this.type = type;
        }

}

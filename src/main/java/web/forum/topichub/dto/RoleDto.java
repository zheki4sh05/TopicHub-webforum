package web.forum.topichub.dto;

public enum RoleDto {
    ADMIN ("ADMIN"),
    USER ("USER");
    private final String type;

    RoleDto(String type) {
        this.type = type;
    }

}


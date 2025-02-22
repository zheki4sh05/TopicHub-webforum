package web.forum.topichub.security.util;

public class PublicPath {

    private PublicPath(){};
    public static final String[] LIST = new String[]{
            "/auth",
            "/api/v1/auth/**",
            "/api/v1/article",
            "/api/v1/article/**",
            "/api/v1/search/**",
            "/api/v1/answers",
            "/api/v1/image",
            "/api/v1/hubs",
            "/swagger-ui/**",
            "/swagger-resources/*",
            "/v3/api-docs/**"
    };

}

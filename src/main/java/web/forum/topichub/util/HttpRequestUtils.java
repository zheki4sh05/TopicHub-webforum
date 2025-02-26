package web.forum.topichub.util;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.security.util.*;

import java.util.*;

@Component
public class HttpRequestUtils {

    @Value("${client.hostName}")
    private String host;

    @Value("${client.port}")
    private String port;

    @Value("${admin.hostName}")
    private String adminHost;

    @Value("${admin.port}")
    private String adminPort;

    @Value("${service.image.uri}")
    private String imageServiceUrl;


    public HttpRequestUtils() {
    }

    public HttpRequestUtils(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public static ArticleFilterDto parseFilterParams(Map<String, String> reqParam) {
        return ArticleFilterDto.builder()
                .month(reqParam.get("month"))
                .year(reqParam.get("year"))
                .rating(reqParam.get("rating"))
                .userId(reqParam.get("userId"))
                .status(reqParam.get("status") == null ? StatusDto.PUBLISH.name() : reqParam.get("status"))
                .param(reqParam.get("hub") == null ? null : Integer.valueOf(reqParam.get("hub")))
                .page( reqParam.get("page") == null ? 1 : Integer.parseInt(reqParam.get("page")))
                .hub(reqParam.get("hub") ==null ? null : Integer.valueOf(reqParam.get("hub")))
                .build();
    }
    public static SearchDto parseSearchParams(Map<String, String> reqParam){
        return SearchDto.builder()
                .author(reqParam.get("author"))
                .theme(reqParam.get("theme"))
                .keywords(reqParam.get("keywords"))
                .articleFilterDto(parseFilterParams(reqParam))
                .build();
    }
    public static boolean contains(String status) {

        for (StatusDto c : StatusDto.values()) {
            if (c.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
    public String getClientUrl(){
        return "http://"+host+":"+port;
    }

    public Boolean isPublic(HttpServletRequest httpRequest){
        var path = httpRequest.getServletPath();
        for (String p : PublicPath.LIST) {
            if (path.contains(p)) {
                return true;
            }
        }
        return false;
    }

    public String getImageServiceUri() {
        return imageServiceUrl;
    }


    public String getImageServiceUri(String id) {
        return imageServiceUrl+"?imageId="+id;
    }

    public String getImageServiceSearchUri(String name){
        return imageServiceUrl+"/search?name="+name;
    }
    public String findImageServiceUri(String id){
        return imageServiceUrl+"/find?imageId="+id;
    }


    public <T,K> ResponseEntity<T> doPost(String uri, MediaType mediaType, K body, Class<T> tClass){
        RestClient restClient = RestClient.create();
        return restClient
                .post()
                .uri(uri)
                .contentType(mediaType)
                .body(body)
                .retrieve()
                .toEntity(tClass);
    }


    public String getAdminUrl() {
        return "http://"+adminHost+":"+adminPort;
    }
}

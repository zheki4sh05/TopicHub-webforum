package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

import java.util.*;


@Mapper(componentModel = "spring", uses = {ArticlePartMapper.class, UserMapper.class})
public interface ArticleMapper{

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(source = "hub.id", target = "hub"),
            @Mapping(source = "articlePartList", target = "list"),
            @Mapping(source = "author", target = "userDto", qualifiedByName = "toAuthor"),
            @Mapping(source = "likes", target = "likes"),
            @Mapping(source = "dislikes", target = "dislikes"),
            @Mapping(source = "comments", target = "commentsCount"),
            @Mapping(source = "state", target = "likeState"),
            @Mapping(source = "previewId", target = "previewId"),
            @Mapping( target = "keyWords", expression = "java(getWords(item.getKeyWords()))")
    })
    ArticleDto toDto(Article item);

    @Named("getWords")
    default List<String> getWords(String words){
        return Arrays.asList(words.split("\\|"));
    }

    @Mappings({
            @Mapping(target = "theme", source = "theme"),
            @Mapping(target = "keyWords", qualifiedByName = "joinWords"),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "status", expression = "java(web.forum.topichub.dto.StatusDto.SANDBOX.name())"),
            @Mapping(target = "hub", ignore = true)
    })
    ArticleEntity fromDto(ArticleDto articleDto);

    @Named("joinWords")
    default String joinWords(List<String> words){
        return String.join("|", words);
    }

}

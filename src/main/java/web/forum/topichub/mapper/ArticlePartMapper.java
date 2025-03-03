package web.forum.topichub.mapper;


import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

@Mapper(componentModel = "spring")
public interface ArticlePartMapper {


    @Mappings({
            @Mapping(target = "uuid", expression = "java(articlePart.getUuid().toString())"),
            @Mapping(source = "value", target = "value"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "created", target = "created"),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "articleId", expression = "java( articlePart.getArticleEntity().getId())")
    })
    ArticlePartDto toDto(ArticlePart articlePart);

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "created", source = "created"),
            @Mapping(target = "article", ignore = true),
            @Mapping(target = "articleEntity", ignore = true)

    })
    ArticlePart fromDto(ArticlePartDto articlePartDto);
}

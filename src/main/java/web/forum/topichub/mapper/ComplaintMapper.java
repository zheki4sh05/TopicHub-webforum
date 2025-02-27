package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.complaints.*;

@Mapper(componentModel = "spring", uses = {ArticleMapper.class, AuthorMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ComplaintMapper {

    @Mapping( target = "id", expression = "java(item.getId().toString())")
    @Mapping(target = "targetId", expression = "java(item.getArticle().getId().toString())")
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "date", target = "date")
    @Mapping(target = "userDto", source = "author")
    ComplaintDto mapFrom(ArticleComplaint item);


    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping( target = "targetId", expression = "java(item.getComment().getId().toString())")
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "date", target = "date")
    @Mapping(target = "userDto", source = "author")
    ComplaintDto mapFrom(CommentComplaint item);
}

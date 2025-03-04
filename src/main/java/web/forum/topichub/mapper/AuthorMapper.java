package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", expression = "java(user.getUuid().toString())")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "logoId", target = "logoId")
    AuthorDto mapFrom(User user);

    @Mapping(target = "id", expression = "java(item.getAuthor().getUuid().toString())")
    @Mapping(target = "email", expression = "java(item.getAuthor().getEmail())")
    @Mapping(target = "login", expression = "java(item.getAuthor().getLogin())")
    @Mapping(target = "logoId", expression = "java(item.getAuthor().getLogoId())")
    AuthorDto mapFrom(Subscription item);


}

package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", expression = "java(user.getUuid().toString())")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    AuthorDto mapFrom(User user);
}

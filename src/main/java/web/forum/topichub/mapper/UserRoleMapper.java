package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.model.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
    @Mapping(target = "role", expression = "java(web.forum.topichub.dto.RoleDto.USER.name())"),
    @Mapping(target = "user", expression = "java(user)")
    })
    UserRole mapFrom(User user);

    @Named("uuid")
    default UUID uuid() {
        return UUID.randomUUID();
    }


}

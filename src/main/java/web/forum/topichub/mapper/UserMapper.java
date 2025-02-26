package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;
import web.forum.topichub.security.dto.*;
import web.forum.topichub.util.*;

import java.util.*;
import java.util.stream.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(user.getUuid().toString())"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "state", target = "state"),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "roles", qualifiedByName = "mapRolesToNames"),
            @Mapping(target = "logoId", source = "logoId"),
    })
    UserDto toDto(User user);

    @Named("mapRolesToNames")
    default List<String> mapRolesToNames(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "status",expression = "java(web.forum.topichub.dto.StatusDto.ACTIVE.name())"),
            @Mapping(target = "state", ignore = true),
            @Mapping(target = "password",  qualifiedByName = "hash"),
            @Mapping(target = "roles", ignore = true)
    })
    User mapFrom(SignUpDto user);

    @Named("hash")
    default String hash(String password) {
        return new PasswordEncoderWrapper().hash(password);
    }


    @Mapping(target = "id", expression = "java(user.getUuid().toString())")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "roles",ignore = true)
    @Mapping(target = "state",ignore = true)
    @Mapping(target = "status",ignore = true)
    @Named("toAuthor")
    UserDto toAuthor(User user);
}

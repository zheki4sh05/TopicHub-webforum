package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.UserDto;
import web.forum.topichub.model.User;
import web.forum.topichub.security.dto.SignUpDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:52+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.email( user.getEmail() );
        userDto.login( user.getLogin() );
        userDto.status( user.getStatus() );
        userDto.roles( mapRolesToNames( user.getRoles() ) );
        userDto.logoId( user.getLogoId() );

        userDto.id( user.getUuid().toString() );

        return userDto.build();
    }

    @Override
    public User mapFrom(SignUpDto user) {
        if ( user == null ) {
            return null;
        }

        User.UserBuilder user1 = User.builder();

        user1.email( user.getEmail() );
        user1.login( user.getLogin() );
        user1.password( hash( user.getPassword() ) );

        user1.uuid( java.util.UUID.randomUUID() );
        user1.status( web.forum.topichub.dto.StatusDto.ACTIVE.name() );

        return user1.build();
    }

    @Override
    public UserDto toAuthor(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.email( user.getEmail() );
        userDto.login( user.getLogin() );
        userDto.logoId( user.getLogoId() );

        userDto.id( user.getUuid().toString() );

        return userDto.build();
    }
}

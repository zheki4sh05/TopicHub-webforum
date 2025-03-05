package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.model.User;
import web.forum.topichub.model.UserRole;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:53+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class UserRoleMapperImpl implements UserRoleMapper {

    @Override
    public UserRole mapFrom(User user) {
        if ( user == null ) {
            return null;
        }

        UserRole.UserRoleBuilder userRole = UserRole.builder();

        userRole.uuid( java.util.UUID.randomUUID() );
        userRole.role( web.forum.topichub.dto.RoleDto.USER.name() );
        userRole.user( user );

        return userRole.build();
    }
}

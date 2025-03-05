package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.AuthorDto;
import web.forum.topichub.model.Subscription;
import web.forum.topichub.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:51+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto mapFrom(User user) {
        if ( user == null ) {
            return null;
        }

        AuthorDto.AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.email( user.getEmail() );
        authorDto.login( user.getLogin() );
        authorDto.logoId( user.getLogoId() );

        authorDto.id( user.getUuid().toString() );

        return authorDto.build();
    }

    @Override
    public AuthorDto mapFrom(Subscription item) {
        if ( item == null ) {
            return null;
        }

        AuthorDto.AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.id( item.getAuthor().getUuid().toString() );
        authorDto.email( item.getAuthor().getEmail() );
        authorDto.login( item.getAuthor().getLogin() );
        authorDto.logoId( item.getAuthor().getLogoId() );

        return authorDto.build();
    }
}

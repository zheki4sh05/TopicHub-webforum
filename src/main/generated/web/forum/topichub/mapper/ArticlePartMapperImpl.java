package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.ArticlePartDto;
import web.forum.topichub.model.ArticlePart;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:51+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ArticlePartMapperImpl implements ArticlePartMapper {

    @Override
    public ArticlePartDto toDto(ArticlePart articlePart) {
        if ( articlePart == null ) {
            return null;
        }

        ArticlePartDto.ArticlePartDtoBuilder articlePartDto = ArticlePartDto.builder();

        articlePartDto.value( articlePart.getValue() );
        articlePartDto.name( articlePart.getName() );
        articlePartDto.type( articlePart.getType() );
        articlePartDto.created( articlePart.getCreated() );
        articlePartDto.id( articlePart.getId() );

        articlePartDto.uuid( articlePart.getUuid().toString() );
        articlePartDto.articleId( articlePart.getArticleEntity().getId() );

        return articlePartDto.build();
    }

    @Override
    public ArticlePart fromDto(ArticlePartDto articlePartDto) {
        if ( articlePartDto == null ) {
            return null;
        }

        ArticlePart.ArticlePartBuilder articlePart = ArticlePart.builder();

        articlePart.id( articlePartDto.getId() );
        articlePart.value( articlePartDto.getValue() );
        articlePart.name( articlePartDto.getName() );
        articlePart.type( articlePartDto.getType() );
        articlePart.created( articlePartDto.getCreated() );

        articlePart.uuid( java.util.UUID.randomUUID() );

        return articlePart.build();
    }
}

package web.forum.topichub.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.ArticleDto;
import web.forum.topichub.dto.ArticlePartDto;
import web.forum.topichub.model.Article;
import web.forum.topichub.model.ArticleEntity;
import web.forum.topichub.model.ArticlePart;
import web.forum.topichub.model.Hub;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:50+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Autowired
    private ArticlePartMapper articlePartMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ArticleDto toDto(Article item) {
        if ( item == null ) {
            return null;
        }

        ArticleDto.ArticleDtoBuilder articleDto = ArticleDto.builder();

        articleDto.id( item.getId() );
        articleDto.hub( itemHubId( item ) );
        articleDto.list( articlePartListToArticlePartDtoList( item.getArticlePartList() ) );
        articleDto.userDto( userMapper.toAuthor( item.getAuthor() ) );
        articleDto.likes( item.getLikes() );
        articleDto.dislikes( item.getDislikes() );
        articleDto.commentsCount( item.getComments() );
        articleDto.likeState( item.getState() );
        articleDto.previewId( item.getPreviewId() );
        articleDto.theme( item.getTheme() );
        articleDto.created( item.getCreated() );

        articleDto.keyWords( getWords(item.getKeyWords()) );

        return articleDto.build();
    }

    @Override
    public ArticleEntity fromDto(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        ArticleEntity.ArticleEntityBuilder articleEntity = ArticleEntity.builder();

        articleEntity.theme( articleDto.getTheme() );
        articleEntity.keyWords( joinWords( articleDto.getKeyWords() ) );
        articleEntity.id( articleDto.getId() );
        articleEntity.previewId( articleDto.getPreviewId() );

        articleEntity.created( java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()) );
        articleEntity.status( web.forum.topichub.dto.StatusDto.SANDBOX.name() );

        return articleEntity.build();
    }

    private Integer itemHubId(Article article) {
        Hub hub = article.getHub();
        if ( hub == null ) {
            return null;
        }
        return hub.getId();
    }

    protected List<ArticlePartDto> articlePartListToArticlePartDtoList(List<ArticlePart> list) {
        if ( list == null ) {
            return null;
        }

        List<ArticlePartDto> list1 = new ArrayList<ArticlePartDto>( list.size() );
        for ( ArticlePart articlePart : list ) {
            list1.add( articlePartMapper.toDto( articlePart ) );
        }

        return list1;
    }
}

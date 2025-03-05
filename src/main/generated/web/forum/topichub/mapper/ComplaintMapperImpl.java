package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.ComplaintDto;
import web.forum.topichub.model.complaints.ArticleComplaint;
import web.forum.topichub.model.complaints.CommentComplaint;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:52+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ComplaintMapperImpl implements ComplaintMapper {

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public ComplaintDto mapFrom(ArticleComplaint item) {
        if ( item == null ) {
            return null;
        }

        ComplaintDto.ComplaintDtoBuilder complaintDto = ComplaintDto.builder();

        if ( item.getDate() != null ) {
            complaintDto.date( item.getDate() );
        }
        if ( item.getAuthor() != null ) {
            complaintDto.userDto( authorMapper.mapFrom( item.getAuthor() ) );
        }
        if ( item.getTitle() != null ) {
            complaintDto.title( item.getTitle() );
        }
        if ( item.getBody() != null ) {
            complaintDto.body( item.getBody() );
        }

        complaintDto.id( item.getId().toString() );
        complaintDto.targetId( item.getArticle().getId().toString() );

        return complaintDto.build();
    }

    @Override
    public ComplaintDto mapFrom(CommentComplaint item) {
        if ( item == null ) {
            return null;
        }

        ComplaintDto.ComplaintDtoBuilder complaintDto = ComplaintDto.builder();

        if ( item.getDate() != null ) {
            complaintDto.date( item.getDate() );
        }
        if ( item.getAuthor() != null ) {
            complaintDto.userDto( authorMapper.mapFrom( item.getAuthor() ) );
        }
        if ( item.getTitle() != null ) {
            complaintDto.title( item.getTitle() );
        }
        if ( item.getBody() != null ) {
            complaintDto.body( item.getBody() );
        }

        complaintDto.id( item.getId().toString() );
        complaintDto.targetId( item.getComment().getId().toString() );

        return complaintDto.build();
    }
}

package web.forum.topichub.mapper;

import lombok.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

import java.util.*;

@Component
@AllArgsConstructor
public class CommentFullMapper {

    private final AuthorMapper authorMapper;

    public CommentDto mapFrom(Comment comment, Long aLong, Set<UUID> processedIds) {

        if (processedIds.contains(comment.getId())) {
            return null;
        }
        processedIds.add(comment.getId());

        CommentDto dto = CommentDto.builder()
                .created(comment.getCreated())
                .id(comment.getId().toString())
                .value(comment.getMessage())
                .articleId(aLong)
                .authorDto(authorMapper.mapFrom(comment.getAuthor()))
                .parentId(comment.getParentComment()!=null ? comment.getParentComment().getId().toString() : null)
                .build();
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            List<CommentDto> replyDtos = new ArrayList<>();
            for (Comment reply : comment.getReplies()) {
                replyDtos.add(mapFrom(reply, aLong, processedIds));
            }
            dto.setReplies(replyDtos);
        }
        return dto;
    }
}

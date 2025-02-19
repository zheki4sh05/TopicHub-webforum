package web.forum.topichub.services.impls;

import jakarta.persistence.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class CommentsService implements ICommentsService {
//    private final IEmailService emailService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentFullMapper commentFullMapper;
    private final ArticleRepo articleRepo;

    @Transactional
    @Override
    public List<CommentDto> fetch(String article) {
        List<Comment> comments = commentRepository.findAllByArticleId(Long.valueOf(article));
        return mapToDtoList(comments,Long.valueOf(article));
    }

    @Override
    @Transactional
    public CommentDto create(CommentDto commentDto, String userId) {
        var article = articleRepo.findById(commentDto.getArticleId()).orElseThrow(EntityNotFoundException::new);
        var author = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityExistsException::new);
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .message(commentDto.getValue())
                .article(article)
                .author(author)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        if(commentDto.getParentId()!=null){
            comment.setParentComment(
                    commentRepository.findById(UUID.fromString(commentDto.getParentId())).orElseThrow(EntityNotFoundException::new)
            );
        }
        commentRepository.save(comment);
//        emailService.sendCommentNotification(comment, article.getAuthor());
       return commentFullMapper.mapFrom(comment, article.getId(), new HashSet<>());
    }

    @Override
    @Transactional
    public CommentDto update(CommentDto commentDto, String userId) {
        Comment comment = commentRepository.findById(UUID.fromString(commentDto.getId())).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){
            comment.setMessage(commentDto.getValue());
           var updated =  commentRepository.save(comment);
            return commentFullMapper.mapFrom(comment, updated.getArticle().getId(), new HashSet<>());
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(String commentId, String userId) {
        Comment comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){
            commentRepository.delete(comment);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public CommentDto findById(String targetId) {
        return null;
    }

    public List<CommentDto> mapToDtoList(List<Comment> comments,Long articleId) {
        Set<UUID> processedIds = new HashSet<>();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(commentFullMapper.mapFrom(comment,articleId, processedIds));
        }
        return commentDtos;
    }
}

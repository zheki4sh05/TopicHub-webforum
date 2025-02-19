package web.forum.topichub.services.impls;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.model.complaints.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class ArticleComplaintService implements IArticleComplaintService {

    private final UserRepository userRepository;
    private final ArticleComplaintRepository articleComplaintRepository;
    private final ArticleRepo articleRepo;
    private final ComplaintMapper complaintMapper;

    @Override
    @Transactional
    public ComplaintDto create(ComplaintDto complaintDto) {
        User user = userRepository.findById(UUID.fromString(complaintDto.getUserDto().getId()))
                .orElseThrow(EntityNotFoundException::new);
        Optional<ArticleComplaint> complaint = articleComplaintRepository.findByArticleUserId(Long.valueOf(complaintDto.getTargetId()), user.getUuid());
        if(complaint.isEmpty()){

           return createAndSaveArticleComplaint(complaintDto, user);

        }else{
            throw new EntityAlreadyExists();
        }
    }

    @Override
    public ComplaintDto findById(String id) {
        ArticleComplaint articleComplaint =   articleComplaintRepository.findById(UUID.fromString(id))
                .orElseThrow(EntityNotFoundException::new);
        return complaintMapper.mapFrom(articleComplaint);
    }

    @Override
    public PageResponse<ComplaintDto> findAll(Pageable pageable) {
        var result = articleComplaintRepository.findAll(pageable);
        return PageResponse.map(complaintMapper::mapFrom, result);
    }

    @Override
    public String deleteById(String id) {
        ArticleComplaint entity= articleComplaintRepository.findByIdArticle(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        articleComplaintRepository.delete(entity);
        return id;
    }

    private ComplaintDto createAndSaveArticleComplaint(ComplaintDto complaintDto, User author) {
        var article = articleRepo.findById(Long.valueOf(complaintDto.getTargetId()))
                .orElseThrow(EntityNotFoundException::new);
        ArticleComplaint articleComplaint = ArticleComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .article(article)
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        var saved =  articleComplaintRepository.save(articleComplaint);
        var dto = complaintMapper.mapFrom(saved);
        dto.setTargetId(String.valueOf(article.getId()));
        return dto;
    }
}

package web.forum.topichub.services.impls;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.model.complaints.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;

@Service
@AllArgsConstructor
public class CommentComplaintService implements ICommentComplaintService {

    private final CommentComplaintRepository commentComplaintRepository;
    private final ComplaintMapper complaintMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ComplaintDto create(ComplaintDto complaintDto) {
        User user = userRepository.findById(UUID.fromString(complaintDto.getUserDto().getId()))
                .orElseThrow(EntityNotFoundException::new);
        Optional<CommentComplaint> complaint = commentComplaintRepository.findByCommentUserId(complaintDto.getTargetId(), user.getUuid());
        if (complaint.isEmpty()) {
          return createAndSaveCommentComplaint(complaintDto, user);
        } else {
            throw new EntityAlreadyExists();
        }
    }

    @Override
    public ComplaintDto findById(String id) {
        CommentComplaint commentComplaint = commentComplaintRepository.findById(UUID.fromString(id))
                .orElseThrow(EntityNotFoundException::new);
        return complaintMapper.mapFrom(commentComplaint);
    }


    private ComplaintDto createAndSaveCommentComplaint(ComplaintDto complaintDto, User author) {
        Comment comment = commentRepository.findById(UUID.fromString(complaintDto.getTargetId()))
                .orElseThrow(EntityNotFoundException::new);
        CommentComplaint complaint = CommentComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .comment(comment)
                .build();
      return complaintMapper.mapFrom(commentComplaintRepository.save(complaint));

    }

    @Override
    public PageResponse<ComplaintDto> findAll(Pageable pageable) {
        var result = commentComplaintRepository.findAllComment(pageable);
        return PageResponse.map(complaintMapper::mapFrom, result);
    }



    @Override
    public String deleteById(String id) {
        CommentComplaint entity= commentComplaintRepository.findByIdComment(id).orElseThrow(EntityNotFoundException::new);
        commentComplaintRepository.delete(entity);
        return id;
    }
}

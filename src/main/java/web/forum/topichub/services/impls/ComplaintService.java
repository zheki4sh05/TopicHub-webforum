package web.forum.topichub.services.impls;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.services.interfaces.*;

import java.util.function.*;

@Service
@AllArgsConstructor
public class ComplaintService implements IComplaintControl {

    private final ICommentComplaintService commentComplaintService;
    private final IArticleComplaintService articleComplaintService;

    @Override
    public void create(ComplaintDto complaintDto) {
        createCommand(complaintDto.getType(),
                articleComplaintService::create,
                commentComplaintService::create
                ,complaintDto);

    }

    @Override
    public ComplaintDto findById(String id, String type) {
       return createCommand(type,
                articleComplaintService::findById,
                commentComplaintService::findById,
                id
                );
    }

    @Override
    public PageResponse<ComplaintDto> findAllByType(String type, Pageable pageable) {
        return  createCommand(type,
                articleComplaintService::findAll,
                commentComplaintService::findAll,
                pageable
        );


    }

    @Override
    public void deleteById(String complaintId, String type) {
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setId(complaintId);
          createCommand(type,
                articleComplaintService::deleteById,
                commentComplaintService::deleteById,
                  complaintId);


    }


    private <T,R> T createCommand(String type, Function<R,T> article, Function<R,T> comment, R dto){
        final String ARTICLE = "article";
        final String COMMENT = "comment";
        switch (type){
            case ARTICLE ->{
                return article.apply(dto);
            }
            case COMMENT ->{
                 return comment.apply(dto);
            }
            default -> throw new BadRequestException();
        }
    }
}

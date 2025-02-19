package web.forum.topichub.services.interfaces;

import org.springframework.data.domain.*;
import web.forum.topichub.dto.*;

public interface IArticleComplaintService {
  ComplaintDto create(ComplaintDto complaintDto);

    ComplaintDto findById(String id);

   PageResponse<ComplaintDto> findAll(Pageable pageable);

    String deleteById(String id);
}

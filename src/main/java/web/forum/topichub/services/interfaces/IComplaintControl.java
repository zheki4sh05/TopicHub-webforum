package web.forum.topichub.services.interfaces;

import org.springframework.data.domain.*;
import web.forum.topichub.dto.*;

public interface IComplaintControl {
    void create(ComplaintDto complaintDto);

    ComplaintDto findById(String id, String type);

    PageResponse<ComplaintDto> findAllByType(String type, Pageable of);

    void deleteById(String complaintId,String type);
}

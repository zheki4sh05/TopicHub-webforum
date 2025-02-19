package web.forum.topichub.services.interfaces;

import org.springframework.data.domain.*;
import web.forum.topichub.dto.*;

public interface IAuthorService {

    void updateUser(UserDto userDto, String userId);

    void delete(String userId);


    UserDto manageBlock(String authorId, String status);

    UserDto findById(String userId);

    PageResponse<UserDto> fetch(String status, Pageable page);

   PageResponse<UserDto> search(String login, String email, Integer page, Boolean isAdmin);

}

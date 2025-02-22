package web.forum.topichub.services.interfaces;

import org.springframework.data.domain.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;

import java.io.*;

public interface IAuthorService {

    void updateUser(UserDto userDto, String userId);

    void delete(String userId);


    UserDto manageBlock(String authorId, String status);

    UserDto findById(String userId);

    PageResponse<UserDto> fetch(String status, Pageable page);

   PageResponse<UserDto> search(String login, String email, Integer page, Boolean isAdmin);

    ImageDto updateImage(String userId, MultipartFile multipartFile) throws IOException;

}

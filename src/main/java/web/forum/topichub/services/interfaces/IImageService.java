package web.forum.topichub.services.interfaces;

import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;

import java.io.*;

public interface IImageService {
    byte[] fetch(String userId);

    ImageDto save(MultipartFile fileContent,String targetId,String imageName) throws IOException;


    void delete(String logoId);

    PageResponse<ImageDto> search(String value, Integer page);

    ImageDto findById(String imageId);
}

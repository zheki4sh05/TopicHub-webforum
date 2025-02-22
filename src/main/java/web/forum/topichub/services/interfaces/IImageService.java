package web.forum.topichub.services.interfaces;

import org.springframework.web.multipart.*;
import web.forum.topichub.dto.client.*;

import java.io.*;

public interface IImageService {
    byte[] fetch(String userId);

    ImageDto save(MultipartFile fileContent) throws IOException;


    void delete(String logoId);
}

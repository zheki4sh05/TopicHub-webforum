package web.forum.topichub.services.interfaces;

import org.springframework.web.multipart.*;

import java.io.*;

public interface IImageService {
    byte[] fetch(String userId);

    void save(String userId, MultipartFile fileContent) throws IOException;
}

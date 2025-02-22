package web.forum.topichub.services.interfaces;

import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;

import java.io.*;

public interface ISandboxService {
    String createSandbox(ArticleDto articleDto, String id);

    String createArticlePart(ArticlePartDto articlePartDto, String id);

    void deletePart(String articleId, String partId, String id);

    void deleteSandbox(String articleId, String id);

    String uploadImage(MultipartFile multipartFile, String id) throws IOException;
}

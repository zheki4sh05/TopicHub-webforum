package web.forum.topichub.services.interfaces;

import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;

import java.io.*;

public interface ISandboxService {
    ArticleDto createSandbox(String id);

    ArticlePartDto createArticlePart(ArticlePartDto articlePartDto, String id);

    void deletePart(String articleId, String partId, String id);

    void clearSandbox(String articleId, String id);

    String uploadImage(MultipartFile multipartFile, String id) throws IOException;

    String createPreview(MultipartFile multipartFile, String id,String imageName) throws IOException;

    void deletePreview(Long articleId,String userId);

    void deleteArticle(String articleId, String id);

    String deleteImage(String imageId);
}

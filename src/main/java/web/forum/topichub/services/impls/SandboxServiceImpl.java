package web.forum.topichub.services.impls;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.ArticlePartType;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.redis.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.io.*;
import java.util.*;

@Service
@AllArgsConstructor
public class SandboxServiceImpl implements ISandboxService {

    private final ArticlePartRepository articlePartRepository;
    private final HubRepository hubDao;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepo articleRepo;
    private final ArticlePartMapper articlePartMapper;
    private final HttpRequestUtils httpResponseUtils;
    private final IImageService imageService;
    private final ImageRedisRestClient imageRedisRestClient;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public ArticleDto createSandbox(String id) {

        Optional<Article> article = articleRepository.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name());
        if(article.isPresent()){
            return articleMapper.toDto(article.get());
        }else{
            User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
            ArticleEntity articleEntity = ArticleEntity.builder()
                    .theme("")
                    .keyWords("")
                    .author(user)
                    .status(StatusDto.SANDBOX.name())
                    .build();
            articleEntity.setAuthor(user);
            var saved = articleRepo.save(articleEntity);
            Article article1 = Article.builder()
                    .id(saved.getId())
                    .keyWords("")
                    .theme("1")
                    .build();
            return articleMapper.toDto(article1);
        }
    }

    @Override
    public ArticlePartDto createArticlePart(ArticlePartDto articlePartDto, String id) {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        ArticlePart articlePart;
        if(articlePartDto.getUuid()==null){
            articlePart= articlePartMapper.fromDto(articlePartDto);
            articlePart.setArticleEntity(article);

        }else{
            articlePart = articlePartRepository.findById(UUID.fromString(articlePartDto.getUuid())).orElseThrow(EntityNotFoundException::new);
            articlePart.setValue(articlePartDto.getValue());
        }
        articlePart =  articlePartRepository.save(articlePart);
        return articlePartMapper.toDto(articlePart);
    }

    @Override
    @Transactional
    public void deletePart(String articleId, String partId, String id) {
//        ArticlePart articlePart = articlePartRepository.findByAuthorArticleAndId(UUID.fromString(id), Long.valueOf(articleId),UUID.fromString(partId));
//        articlePartRepository.delete(articlePart);
        articlePartRepository.deleteByIdAndArticle(UUID.fromString(partId), articleId, UUID.fromString(id));
    }

    @Override
    @Transactional
    public void  clearSandbox(String articleId, String id) {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        article.setTheme("");
        article.setHub(null);
        article.setKeyWords("");
        if(article.getPreviewId()!=null){
            imageService.delete(article.getPreviewId());
        }
        articleRepo.save(article);
        articlePartRepository.deleteByArticleId(article.getId());
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, String id) throws IOException {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        ImageDto imageDto = imageService.save(multipartFile, article.getId().toString(), multipartFile.getOriginalFilename());
        ArticlePart articlePart = ArticlePart.builder()
                .articleEntity(article)
                .name("Загрузить изображение")
                .type(ArticlePartType.IMG_LOAD.type())
                .created(System.currentTimeMillis())
                .id(5)
                .value(imageDto.getId())
                .uuid(UUID.randomUUID())
                .build();
        articlePart= articlePartRepository.save(articlePart);
       return articlePart.getUuid().toString();
    }

    @Override
    public String createPreview(MultipartFile multipartFile, String id,String imageName) throws IOException {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        ImageDto imageDto = imageService.save(multipartFile, article.getId().toString(), imageName);
        if(article.getPreviewId()!=null){
            imageService.delete(article.getPreviewId());
        }
        article.setPreviewId(imageDto.getId());
        imageRedisRestClient.save(imageDto);
        articleRepo.save(article);
        return imageDto.getId();

    }

    @Override
    public void deletePreview(Long articleId,String userId) {
        ArticleEntity article = articleRepo.findByIdAndAuthor(articleId, UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
        imageService.delete(article.getPreviewId());
        article.setPreviewId(null);
        articleRepo.save(article);
    }

    @Override
    public void deleteArticle(String articleId, String id) {
        articleRepository.deleteByIdAndArticleId(Long.valueOf(articleId), UUID.fromString(id));

    }

    @Override
    public String deleteImage(String imageId) {
        ArticlePart articlePart = articlePartRepository.findById(UUID.fromString(imageId)).orElseThrow(EntityNotFoundException::new);
        imageService.delete(articlePart.getValue());
        articlePartRepository.delete(articlePart);
        return imageId;
    }

//    @Override
//    public ArticleDto findSandbox(String id) {
//
//        return articleMapper.toDto(article);
//    }


}

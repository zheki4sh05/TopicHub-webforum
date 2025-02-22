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


    @Override
    @Transactional
    public String createSandbox(ArticleDto articleDto, String id) {
        List<Hub> hubList = hubDao.findAll();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        ArticleEntity article = articleMapper.fromDto(articleDto);
        article.setAuthor(user);
        article.setHub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElse(null));
        article = articleRepo.save(article);
        return article.getId().toString();

    }

    @Override
    public String createArticlePart(ArticlePartDto articlePartDto, String id) {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        ArticlePart articlePart = articlePartMapper.fromDto(articlePartDto);
        articlePart.setArticleEntity(article);
        return articlePartRepository.save(articlePart).getUuid().toString();
    }

    @Override
    public void deletePart(String articleId, String partId, String id) {
        ArticlePart articlePart = articlePartRepository.findByAuthorArticleAndId(UUID.fromString(id), Long.valueOf(articleId),UUID.fromString(partId));
        articlePartRepository.delete(articlePart);
    }

    @Override
    public void deleteSandbox(String articleId, String id) {
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        articleRepo.delete(article);
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, String id) throws IOException {
        ImageDto imageDto = imageService.save(multipartFile);
        ArticleEntity article = articleRepo.findSandboxByUser(UUID.fromString(id), StatusDto.SANDBOX.name()).orElseThrow(EntityNotFoundException::new);
        ArticlePart articlePart = ArticlePart.builder()
                .articleEntity(article)
                .type(ArticlePartType.IMG_LOAD.type())
                .value(httpResponseUtils.getImageServiceUri(imageDto.getId()))
                .uuid(UUID.randomUUID())
                .build();

       return articlePartRepository.save(articlePart).getUuid().toString();

    }


}

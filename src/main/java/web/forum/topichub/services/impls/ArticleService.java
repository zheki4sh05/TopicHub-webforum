package web.forum.topichub.services.impls;

import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.filters.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@AllArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticlePartRepository articlePartRepository;
    private final HubRepository hubDao;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepo articleRepo;
    private final ArticleViewRepository articleViewRepository;
    private final FilterQueryFactory filterQueryFactory;
    private final ArticlePartMapper articlePartMapper;

    @Override
    @Transactional
    public Long create(ArticleDto articleDto, String id) {
        List<Hub> hubList = hubDao.findAll();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        final var article = articleMapper.fromDto(articleDto);
        article.setAuthor(user);
        article.setHub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElse(null));
      Long savedId = articleRepo.save(article).getId();
        articleDto.getList().forEach(item->{
            var newPart = articlePartMapper.fromDto(item);
            newPart.setArticleEntity(article);
            articlePartRepository.save(newPart);
        });
        return savedId;
    }

    @Override
    @Transactional
    public Long publish(ArticleDto articleDto, String id) {
        ArticleEntity articleEntity = articleRepo.findById(articleDto.getId()).orElseThrow(EntityNotFoundException::new);
        articleEntity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        articleEntity.setStatus(StatusDto.MODERATION.name());
        articleRepo.save(articleEntity);
        return articleDto.getId();
    }

    @Override
    public void delete(String id, String userId) {
        var article = articleRepo.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
        if(article.getAuthor().getUuid().toString().equals(userId)){
            articleRepo.delete(article);
        }else{
            throw new EntityNotFoundException(ErrorKey.NOT_FOUND.key());
        }
    }

    @Override
    @Transactional
    public PageResponse<ArticleDto> search(SearchDto searchDto) {
        Pageable pageable= PageRequest.of(searchDto.getArticleFilterDto().getPage()-1,15);
        Page<Article> articles = articleViewRepository.searchBy(searchDto.getAuthor(),
                searchDto.getTheme(),
                "%"+searchDto.getKeywords()+"%",
                 pageable);
        PageResponse<Article> pageResponse = PageResponse.map(articles);
        checkLike(searchDto.getUserId(), pageResponse);
        return PageResponse.map(articleMapper::toDto, articles);

    }
    @Override
    @Transactional(readOnly = true)
    public PageResponse<ArticleDto> fetchBookMarks(String userId, Integer page) {
        Pageable pageable= PageRequest.of(page-1,15);
        Page<Article> articles = articleViewRepository.findBookmarks(UUID.fromString(userId),pageable);
        PageResponse<Article> pageResponse = PageResponse.map(articles);
        checkLike(userId, pageResponse);
        return PageResponse.map(articleMapper::toDto, articles);
    }



    @Override
    @Transactional(readOnly = true)
    public PageResponse<ArticleDto> fetch(ArticleFilterDto articleFilter) {
        CriteriaQuery<Article> articleCriteriaQuery = filterQueryFactory.createQuery(articleFilter);
        PageResponse<Article> articlePage = articleRepository.findByQuery(
                articleCriteriaQuery,
                PageRequest.of(articleFilter.getPage(),15));
        checkLike(articleFilter.getUserId(), articlePage);
        return PageResponse.map(articleMapper::toDto, articlePage);
    }
    @Override
    public void deleteAdmin(String targetId) {
        var article = articleRepo.findById(Long.parseLong(targetId)).orElseThrow(EntityNotFoundException::new);
        articleRepo.delete(article);
    }

    @Override
    @Transactional
    public void update(ArticleDto updatedArticle, String id) {
        List<Hub> hubList = hubDao.findAll();
        var updateEntity = articleRepo.findById(updatedArticle.getId()).orElseThrow(()->new EntityNotFoundException());
     updateEntity.setKeyWords(articleMapper.joinWords(updatedArticle.getKeyWords()));
     updateEntity.setTheme(updatedArticle.getTheme());
     updateEntity.setHub(hubList.stream().filter(item->item.getId().equals(updatedArticle.getHub())).findFirst().orElseThrow(EntityNotFoundException::new));
    }


    @Override
    @Transactional
    public void update(ArticleStatusDto articleStatusDto) {
            var article = articleRepo.findById(Long.valueOf(articleStatusDto.getId()))
                    .orElseThrow(EntityNotFoundException::new);
            article.setStatus(articleStatusDto.getStatus());
            articleRepo.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto findById(String id) {
        var a = articleViewRepository.findById(Long.parseLong(id));
        return articleMapper.toDto(a
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public String getStatusNameById(String id) {
        return articleViewRepository.getStatus(id);
    }

    @Override
    public void createArticle(Long articleId, String id) {
      var article = articleRepo.findByIdAndAuthor(articleId, UUID.fromString(id))
                .map(item->{
                    item.setStatus(StatusDto.MODERATION.name());
                    return item;
                })
                .orElseThrow(EntityNotFoundException::new);
        articleRepo.save(article);
    }

    @Override
    public void makeEdit(Long articleId) {
        ArticleEntity articleEntity = articleRepo.findById(articleId).orElseThrow(EntityNotFoundException::new);
        articleEntity.setStatus(StatusDto.EDIT.name());
        articleRepo.save(articleEntity);
    }

    private void checkLike(String userId, PageResponse<Article> pageResponse){
        if(userId!=null){
            pageResponse.getItems().forEach(item->item.setState(
                    likeRepository.userLikeState(
                            UUID.fromString(userId),
                            item.getId()
                    )));
        }
    }



}

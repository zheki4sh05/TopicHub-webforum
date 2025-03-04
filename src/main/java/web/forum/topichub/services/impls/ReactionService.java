package web.forum.topichub.services.impls;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.model.complaints.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;
import java.util.stream.*;

@Service
@Slf4j
@AllArgsConstructor
public class ReactionService implements IReactionService {

    private final BookmarkRepository bookmarkRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ArticleRepo articleRepository;
    private final AuthorMapper authorMapper;
    private final Integer PAGE_SIZE=15;
    @Override
    public ReactionDto check(String articleId, String authorId, String userId) {
        Boolean subscribe =subscriptionRepository.checkSubscribe(UUID.fromString(userId),UUID.fromString(authorId));
        Boolean marked = bookmarkRepository.checkMarked(UUID.fromString(userId), Long.valueOf(articleId));
        return ReactionDto.builder()
                .isMarked(marked)
                .isSubscribe(subscribe)
                .build();
    }

    @Override
    public void makeReaction(String type, Integer value, String userId, Long targetId) {
        switch (type) {
            case "article" -> {
                var article = articleRepository.findById(targetId).orElseThrow(EntityNotFoundException::new);
                User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
                Optional<Likes> reaction = likeRepository.findById(article.getId(), user.getUuid());
                reaction.ifPresentOrElse(
                        (item) -> updateReaction(item, value),
                        () -> createNewReaction(article, user, value)
                );
            }
            case "comment" -> throw new UnsupportedException();
            default -> throw new BadRequestException();
        }
    }

    private void updateReaction(Likes item, Integer value) {
        item.setState(value);
        likeRepository.save(item);
    }

    private void createNewReaction(ArticleEntity article, User user, Integer value) {
        likeRepository.save(Likes.builder()
                .uuid(UUID.randomUUID())
                .state(value)
                .article(article)
                .user(user)
                .build());
    }

    @Override
    public void manageSubscription(Integer value, String authorId, String userId) {
        User user  = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
        User author = userRepository.findById(UUID.fromString(authorId)).orElseThrow(EntityNotFoundException::new);
       if(value==1){
           subscriptionRepository.save(Subscription.builder()
                           .id(UUID.randomUUID())
                           .author(author)
                           .follower(user)
                   .build());
       }else{
           var subscription = subscriptionRepository.findByUsers(author.getUuid(), user.getUuid()).orElseThrow(()->new EntityNotFoundException(ErrorKey.NOT_FOUND.name()));
           subscriptionRepository.delete(subscription);
       }

    }

    @Override
    public void manageBookmarks(Integer value, String articleId, String userId) {
        User user  = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
        var article = articleRepository.findById(Long.valueOf(articleId)).orElseThrow(EntityNotFoundException::new);
        if(value==1){
            var entity = Bookmark.builder()
                    .id(UUID.randomUUID())
                    .article(article)
                    .author(user)
                    .build();
            bookmarkRepository.save(entity);
        }else{
            var bookmark = bookmarkRepository.findByUserIdArticleId(UUID.fromString(userId),Long.valueOf(articleId))
                    .orElseThrow(()-> new EntityNotFoundException(ErrorKey.NOT_FOUND.name()));
            bookmarkRepository.delete(bookmark);
        }
    }

    @Override
    public void removeReaction(String type, String userId, Long articleId) {
        switch (type) {
            case "article" -> {
                var like = likeRepository.findById(articleId, UUID.fromString(userId))
                        .orElseThrow(() -> new EntityNotFoundException(ErrorKey.NOT_FOUND.name()));
                likeRepository.delete(like);
            }
            case "comment" -> throw new UnsupportedException();
            default -> throw new BadRequestException();
        }
    }

    @Override
    public PageResponse<AuthorDto> fetchAllSubscribes(String id,Integer page) {
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE);
        Page<Subscription> subscriptions = subscriptionRepository.fetch(UUID.fromString(id),pageable);
//        return subscriptions.stream().map(item->AuthorDto.builder()
//                .login(item.getAuthor().getLogin())
//                .email(item.getAuthor().getEmail())
//                .id(item.getAuthor().getUuid().toString())
//                .build()).collect(Collectors.toList());
        return  PageResponse.map(authorMapper::mapFrom, subscriptions);
    }

    @Override
    public  PageResponse<AuthorDto> fetchAllFollowers(String id,Integer page) {
        Pageable pageable= PageRequest.of(page-1,PAGE_SIZE);
        Page<Subscription> userList = subscriptionRepository.findFollowersById(UUID.fromString(id),pageable);
        return PageResponse.map(authorMapper::mapFrom, userList);
    }
}

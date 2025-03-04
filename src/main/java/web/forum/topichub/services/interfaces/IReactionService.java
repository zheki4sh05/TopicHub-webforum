package web.forum.topichub.services.interfaces;

import org.springframework.data.domain.*;
import web.forum.topichub.dto.*;

import java.util.*;

public interface IReactionService {
    ReactionDto check(String articleId, String authorId, String userId);

    void makeReaction(String type, Integer value, String userId, Long targetId);

    void manageSubscription(Integer value, String authorEmail, String email);

    void manageBookmarks(Integer value, String article, String userId);

    void removeReaction(String type, String userId, Long aLong);

    PageResponse<AuthorDto> fetchAllSubscribes(String id, Integer page);

    PageResponse<AuthorDto> fetchAllFollowers(String id,Integer page);
}

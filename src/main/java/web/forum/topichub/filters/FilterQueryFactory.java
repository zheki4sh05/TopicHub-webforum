package web.forum.topichub.filters;


import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.filter.*;
import web.forum.topichub.model.*;

import java.util.*;

@Service
@AllArgsConstructor
public class FilterQueryFactory {

    private final CriteriaBuilder criteriaBuilder;
    private Predicate createLikesPredicate(Root<Article> articleRoot, Double rating){
        Double fraction = rating/100;
        Expression<Long> likes = articleRoot.get("likes");
        Expression<Long> dislikes = articleRoot.get("dislikes");
        Expression<Long> total = criteriaBuilder.sum(likes, dislikes);
        Expression<Number> percent = criteriaBuilder.prod(total, fraction);
        return criteriaBuilder.gt(likes, percent);
    }

    public List<Predicate> createPredicates(IFactoryFilterDataSupplier factoryFilterDataSupplier, Root<Article> articleRoot){

        Expression<Integer> monthExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("month"),articleRoot.get("created")
        );
        Expression<Integer> yearExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("year"), articleRoot.get("created")
        );
        Predicate monthPredicate = criteriaBuilder.equal(monthExpression, factoryFilterDataSupplier.getMonth());
        Predicate yearPredicate = criteriaBuilder.equal(yearExpression, factoryFilterDataSupplier.getYear());
        List<Predicate> list  = new ArrayList<>();
        list.add(monthPredicate);
        list.add(yearPredicate);
        return list;
    }

    public CriteriaQuery<Article> createQuery(ArticleFilterDto articleFilterDto){
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> articleRoot = criteriaQuery.from(Article.class);
        List<Predicate> predicates = createPredicatesByFilter(articleFilterDto, criteriaBuilder, articleRoot,criteriaQuery);
        criteriaQuery.select(articleRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(criteriaBuilder.asc(articleRoot.get("created")));
        return criteriaQuery;
    }

    private List<Predicate> createPredicatesByFilter(ArticleFilterDto articleFilterDto, CriteriaBuilder criteriaBuilder, Root<Article> articleRoot,CriteriaQuery<Article> criteriaQuery) {
        List<Predicate> predicates = new ArrayList<>();
        if(articleFilterDto.getMonth()!=null && articleFilterDto.getYear()!=null){
            predicates.addAll(createPredicates(articleFilterDto, articleRoot));
        }
        predicates.add(createStatusPredicate(criteriaBuilder, articleRoot, articleFilterDto.getStatus()));

        if(articleFilterDto.getRating()!=null){
            predicates.add(createLikesPredicate(articleRoot, Double.valueOf(articleFilterDto.getRating())));
        }
        predicates.addAll(createJoins(articleFilterDto, criteriaQuery, articleRoot));


        if(articleFilterDto.getAuthorId()!=null
                && articleFilterDto.getUserId()!=null
                &&  articleFilterDto.getAuthorId().equals(articleFilterDto.getUserId())){
            predicates.add(createOwnPredicate(criteriaBuilder, articleRoot, articleFilterDto.getAuthorId()));
        }
        return predicates;

    }

    private Predicate createOwnPredicate(CriteriaBuilder criteriaBuilder, Root<Article> articleRoot, String authorId) {
        Join<Article, User> authorJoin = articleRoot.join("author", JoinType.INNER);
        return criteriaBuilder.equal(authorJoin.get("uuid"), UUID.fromString(authorId));
    }

    private Predicate createStatusPredicate(CriteriaBuilder criteriaBuilder, Root<Article> articleRoot, String value) {
        return criteriaBuilder.equal(articleRoot.get("status"), value);
    }

    public List<Predicate> createJoins(IBusinessLogicFilterSupplier articleFilter, CriteriaQuery<Article> criteriaQuery, Root<Article> articleRoot) {
        List<Predicate> predicates  = new ArrayList<>();
        if(articleFilter.getParam() == null){
            return new ArrayList<>();
        }
        if( articleFilter.getParam()==-1 && articleFilter.getUserId()!=null){
            Root<Subscription> subscription = criteriaQuery.from(Subscription.class);
            var id = articleFilter.getUserId();
            Predicate joinCondition = criteriaBuilder.and(
                    criteriaBuilder.equal(subscription.get("author").get("uuid"), articleRoot.get("author").get("uuid")),
                    criteriaBuilder.equal(subscription.get("follower").get("uuid"), UUID.fromString(id))
            );
            predicates.add(joinCondition);
        }else if(articleFilter.getParam()>0){
            Join<Article, Hub> hubJoin = articleRoot.join("hub", JoinType.INNER);
            Predicate hubPredicate = criteriaBuilder.equal(hubJoin.get("id"), articleFilter.getParam());
            predicates.add(hubPredicate);
        }
        return predicates;
    }


}
package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface ArticlePartRepository extends JpaRepository<ArticlePart, UUID> {


}

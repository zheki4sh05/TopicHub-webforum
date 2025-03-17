package web.forum.topichub.repository;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import web.forum.topichub.redis.*;

@Repository
public interface ImageRedisRepository extends CrudRepository<ImageCache, String> {

    Page<ImageCache> findByMetaName(String value, PageRequest pageRequest);
}

package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="likes")
public class Likes {

    @Column(name = "id")
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @Column(name = "state")
    private Integer state;

}

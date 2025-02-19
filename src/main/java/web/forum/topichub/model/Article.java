package web.forum.topichub.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="article_info")
@NamedEntityGraph(name = "article.articlePartList", attributeNodes = @NamedAttributeNode("articlePartList"))
public class Article {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Column(name = "comments_count")
    private Integer comments;

    @Column(name = "keywords")
    private String keyWords;

    @Column(name = "status")
    private String status;

    @Transient
    @Setter
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "hub")
    private Hub hub;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created")
    private Timestamp created;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticlePart> articlePartList;
}

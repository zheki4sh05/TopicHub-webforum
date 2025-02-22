package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "articlepart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticlePart {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @EqualsAndHashCode.Include
    @Column(name = "val")
    private String value;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Long created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article", insertable = false, updatable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article")
    private ArticleEntity articleEntity;
}

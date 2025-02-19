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
public class ArticlePartEntity {
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;

    @Column(name = "type")
    private String type;

    @Column(name = "val")
    @EqualsAndHashCode.Include
    private String value;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Long created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private ArticleEntity articleEntity;

}


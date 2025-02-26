package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theme")
    private String theme;


    @Column(name = "keywords")
    private String keyWords;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "hub")
    private Hub hub;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created")
    private Timestamp created;

    @Column(name="preview_id")
    private String previewId;


}

package web.forum.topichub.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@NamedEntityGraph(
        name = "comment-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("replies"),
        }
)
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "message")
    private String message;

    @Column(name = "created")
    private Timestamp created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;
}

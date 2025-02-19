package web.forum.topichub.model.complaints;

import jakarta.persistence.*;
import lombok.*;
import web.forum.topichub.model.*;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="complaint_article")
public class ArticleComplaint {
    @Column(name = "id")
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

}

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
@Table(name="complaint_comment")
public class CommentComplaint {
    @Column(name = "id")
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

}

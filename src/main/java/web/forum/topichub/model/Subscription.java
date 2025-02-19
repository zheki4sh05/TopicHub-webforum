package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
@Builder
public class Subscription {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private User follower;

}

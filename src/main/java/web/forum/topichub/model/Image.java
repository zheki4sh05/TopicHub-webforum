package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;
import web.forum.topichub.dto.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="image")
public class Image {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "image_id")
    private String imageId;

}

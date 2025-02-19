package web.forum.topichub.security.model;

import jakarta.persistence.*;
import lombok.*;
import web.forum.topichub.model.*;

import java.util.*;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;
    public Boolean getLoggedOut() {
        return loggedOut;
    }

    @Column(name = "is_logged_out")
    private Boolean loggedOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}

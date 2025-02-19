package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="user_role")
public class UserRole implements GrantedAuthority {

    @Column(name = "id")
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private User user;

    @Column(name = "role")
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}

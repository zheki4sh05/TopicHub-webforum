package web.forum.topichub.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="author")
public class User implements UserDetails {
    @Column(name = "id")
    @Id
    private UUID uuid;

    @Column(name = "login",unique = true)
    private String login;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private String status;

    @ToString.Exclude
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package web.forum.topichub.util;

import org.springframework.security.crypto.bcrypt.*;

public class PasswordEncoderWrapper {
    public String hash(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean matches(String password, String password1) {
        return new BCryptPasswordEncoder().matches(password, password1);
    }
}

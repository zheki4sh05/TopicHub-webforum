package web.forum.topichub.security.util;

import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public String getUserId(){
        return getPrincipal().getUuid().toString();
    }


}

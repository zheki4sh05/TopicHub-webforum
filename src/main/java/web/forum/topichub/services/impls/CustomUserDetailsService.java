package web.forum.topichub.services.impls;

import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import web.forum.topichub.repository.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(cacheManager ="cacheManager" , cacheNames = "userDetails", key = "#username")
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository.getByEmailOrLogin(username);
    }
}
package web.forum.topichub.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthorService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    @Override
    public void updateUser(UserDto userDto, String userId) {
        try{
            User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
            user.setEmail(userDto.getEmail());
            user.setLogin(userDto.getLogin());
            userRepository.save(user);
        }catch (RollbackException e){
            throw new EntityAlreadyExists();
        }
    }

    @Override
    public void delete(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if(user.isPresent()) {
            List<UserRole> userRoles = user.get().getRoles();
            userRoleRepository.deleteAll(userRoles);
            userRepository.delete(user.get());
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.name());
        }

    }
    @Override
    public UserDto manageBlock(String authorId, String status) {
        Optional<User> user = userRepository.findById(UUID.fromString(authorId));
        if(user.isPresent()){
            var item = user.get();
            item.setStatus(status);
            userRepository.save(item);
            return userMapper.toDto(item);
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.name());
        }
    }

    @Override
    public UserDto findById(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public PageResponse<UserDto> fetch(String status, Pageable page) {
      Page<User> userList = userRepository.findByStatus(status,page);
        return PageResponse.map(userMapper::toDto, userList);
    }

    @Override
    public PageResponse<UserDto> search(String login, String email, Integer page, Boolean isAdmin) {
        Page<User> userList = userRepository.searchByLoginOrEmail(login,email, PageRequest.of(page-1,15));
        if(isAdmin){
            return PageResponse.map(userMapper::toDto, userList);
        }else{
            return PageResponse.map(userMapper::toAuthor, userList);
        }
    }



}

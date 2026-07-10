package web.forum.topichub.services.impls;


import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.*;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;
import web.forum.core.dto.EmailDto;
import web.forum.topichub.config.i18n.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.message.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.security.service.*;
import web.forum.topichub.services.interfaces.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialsService implements ICredentialsService {

    @Value("${application.credentials.code.length}")
    private Integer codeSize;

    @Value("${application.credentials.code.life}")
    private Integer codeLifeTime;
    private final UserRepository userRepository;

    @Value("${application.kafka.topic.emailSend}")
    private String topicName;

    private final I18nUtil i18nUtil;
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final JwtService jwtService;

    private final CacheManager cacheManager;
    private final IAuthenticationService authorService;
    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public void generateCode(CodeDto request, Locale locale) {
            userRepository.findByEmailOrLogin(request.getEmail()).orElseThrow(EntityNotFoundException::new);
            String code = generateCode(codeSize);
            String email  = request.getEmail();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiresAt = now.plusMinutes(codeLifeTime);
            PasswordResetCode resetCode = new PasswordResetCode(email, code, now, expiresAt);
            saveToCache(email, resetCode);
            var kafkaRequest = kafkaTemplate.send("email.send", createCodeEmailMessage(resetCode, locale));
            kafkaRequest.whenComplete((result, exception)->{
                if(exception!=null){
                    log.error("failed kafka send message {}", exception.getMessage());
                }else{
                    log.info("message sent successfully:{}", result.getRecordMetadata());
                }
            });
    }



    private Object createCodeEmailMessage(PasswordResetCode resetCode, Locale locale) {
        return  EmailDto.builder()
                .subject(i18nUtil.getMessage(EmailCode.MSG_SBJ_PASS_RECOVERY.key(), locale,null))
                .title(i18nUtil.getMessage(EmailCode.MSG_TITLE_PASS_RECOVERY.key(),locale,null))
                .body(i18nUtil.getMessage(EmailCode.MSG_BODY_PASS_RECOVERY.key(),locale,null) + " "+resetCode.getCode())
                .to(resetCode.getEmail())
                .build();
    }

    public PasswordResetCode saveToCache(String email, PasswordResetCode passwordResetCode){

        redisTemplate.opsForValue().set("codes:"+email, passwordResetCode);
        return passwordResetCode;

    }

    public String getCodeFromCache(CodeDto codeDto){
        PasswordResetCode passwordResetCode = (PasswordResetCode) redisTemplate.opsForValue().get("codes:"+codeDto.getEmail());

        if(passwordResetCode.getCode().equals(codeDto.getCode()) &&
                passwordResetCode.getExpiresAt().isAfter(LocalDateTime.now())){
            return codeDto.getEmail();
        }else{
            throw new TokenExpiredException();
        }


    }
//    @CacheEvict(cacheManager ="codeCacheManager" , cacheNames = "codes", key = "#email")
    public void deleteFromCache(String email){
        redisTemplate.delete("code:"+email);
    }

    @Override
    public String submitCode(CodeDto request) {
      String email =  getCodeFromCache(request);
      deleteFromCache(email);
      String token = jwtService.generateAccessToken(User.builder()
                        .email(email)
                .build());
      saveTokenToCache(email, token);
      return token;

    }

//    @CachePut(cacheManager ="tokenEmailCacheManager" , cacheNames = "tokensEmail", key = "#token")
    public String saveTokenToCache(String email, String token) {
        redisTemplate.opsForValue().set("tokensEmail:"+token, email);
        return email;
    }

    public String getTokenEmailFromCache(String token){
        if(token==null){
            throw new NullPointerException();
        }
        Object result =  redisTemplate.opsForValue().get("tokensEmail:"+token);
        if(result!=null){
            return (String) result;
        }else{
            throw new EntityNotFoundException();
        }

    }


    @Override
    public void update(CredentialsDto credentialsDto) {
        getTokenEmailFromCache(credentialsDto.getCode());
        authorService.updatePassword(credentialsDto);
    }

    private String generateCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}

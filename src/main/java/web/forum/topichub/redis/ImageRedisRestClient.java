package web.forum.topichub.redis;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.core.*;
import org.springframework.data.domain.*;
import org.springframework.data.redis.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.util.*;

import java.util.*;

@AllArgsConstructor
@Component
@Slf4j
public class ImageRedisRestClient {

    private final ImageRedisRepository imageRedisRepository;
    private final ImageMapper imageMapper;
    private final HttpRequestUtils httpRequestUtils;

    public PageResponse<ImageDto> findByName(String value, PageRequest pageRequest) {
        Page<ImageCache> imageCachePage = checkRedisCache(value, pageRequest);
        if (imageCachePage != null && imageCachePage.getContent().size()!=0) {
            log.info("data from cache {}", imageCachePage.getTotalPages());
            return PageResponse.map(imageMapper::toDto, imageCachePage);
        }

        RestClient restClient = RestClient.create();
        ResponseEntity<PageResponse<ImageDto>> imageDtoResponseEntity =  restClient
                .get()
                .uri(httpRequestUtils.getImageServiceSearchUri(value))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<PageResponse<ImageDto>>(){});

        PageResponse<ImageDto> imageDtoPageResponse = imageDtoResponseEntity.getBody();
        if (imageDtoPageResponse.getItems().size() > 0) {
            cacheList(imageDtoPageResponse.getItems());
        }
        return imageDtoPageResponse;
    }

    private void cacheList(List<ImageDto> items) {
        List<ImageCache> imageCacheList = new ArrayList<>();
        items.forEach(item -> {
            ImageDto imageDto = item;
            ImageCache ibj = imageMapper.fromDto(imageDto);
                    imageCacheList.add(ibj);
                }
               );

//        try{
      imageRedisRepository.saveAll(imageCacheList);

//        }catch (RedisConnectionFailureException e){
//            System.out.println(e.getMessage());
//                log.error("redis connection error {}", e.getMessage());
//        }
    }

    private Page<ImageCache> checkRedisCache(String value, PageRequest pageRequest){
        try{
            return imageRedisRepository.findByFilename(value, pageRequest);
        }catch (Exception e){
            return null;
        }
    }

    public void save(ImageDto imageDto) {
        ImageCache imageCache = imageMapper.fromDto(imageDto);
        try{
            imageRedisRepository.save(imageCache);
        }catch (RedisConnectionFailureException e){
            log.error("redis connection error {}", e.getMessage());
        }
    }

    public ImageDto findById(String imageId) {
        ImageCache imageCachePage = checkRedisCache(imageId);
        if (imageCachePage != null) {
            return  imageMapper.toDto(imageCachePage);
        }

        RestClient restClient = RestClient.create();
        ResponseEntity<ImageDto> imageDtoResponseEntity =  restClient
                .get()
                .uri(httpRequestUtils.findImageServiceUri(imageId))
                .retrieve()
                .toEntity(ImageDto.class);

        ImageDto imageDtoPageResponse = imageDtoResponseEntity.getBody();
        try{
            imageRedisRepository.save(imageMapper.fromDto(imageDtoPageResponse));
        }catch (RedisConnectionFailureException e){
            log.error("redis connection error {}", e.getMessage());
        }
        return imageDtoPageResponse;
    }

    private ImageCache checkRedisCache(String imageId) {
        try{
            var result = imageRedisRepository.findById(imageId);
            return result.orElse(null);
        }catch (Exception e) {
            return null;
        }
    }
}

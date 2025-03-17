package web.forum.topichub.services.impls;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.core.io.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.redis.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.io.*;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService implements IImageService {

    private final HttpRequestUtils httpRequestUtils;
    private final ImageRedisRestClient imageRedisRestClient;
    private final RestClient restClient;

    @Override
    public byte[] fetch(String imageId) {
       ResponseEntity<ByteArrayResource> imageDtoResponseEntity =  restClient
                .get()
                .uri(httpRequestUtils.getImageServiceUri(imageId))
                .retrieve()
                .toEntity(ByteArrayResource.class);
        return imageDtoResponseEntity.getBody().getByteArray();

    }

    @Override
    public ImageDto save(MultipartFile fileContent,String targetId,String imageName) throws IOException {
        byte[] bytes = fileContent.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes){
            @Override
            public String getFilename(){
                return fileContent.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> map  = new LinkedMultiValueMap<>();
        map.add("file", byteArrayResource);
        map.add("targetId", targetId);
        map.add("name", imageName);
        ResponseEntity<ImageDto> imageDtoResponseEntity = new ResponseEntity<>(HttpStatusCode.valueOf(500));
        try{
            imageDtoResponseEntity   = restClient
                    .post()
                    .uri(httpRequestUtils.getImageServiceUri())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(map)
                    .retrieve()
                    .toEntity(ImageDto.class);
        }catch (RuntimeException e){
            throw new InternalServerErrorException(ErrorKey.IMAGE_LOAD_ERROR.key());
        }

        return imageDtoResponseEntity.getBody();

        }



    @Override
    public void delete(String imageId) {
        RestClient restClient = RestClient.create();
         restClient
                .delete()
                .uri(httpRequestUtils.getImageServiceUri(imageId))
                .retrieve()
                .toEntity(String.class);
    }

    @Override
    public PageResponse<ImageDto> search(String value, Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1, 15);
        return imageRedisRestClient.findByName(value, pageRequest);
    }

    @Override
    public ImageDto findById(String imageId) {
        return imageRedisRestClient.findById(imageId);
    }


}

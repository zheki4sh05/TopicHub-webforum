package web.forum.topichub.services.impls;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.io.*;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService implements IImageService {

    private final HttpRequestUtils httpRequestUtils;
    private final UserDataRepository userDataRepository;


    @Override
    public byte[] fetch(String imageId) {
        RestClient restClient = RestClient.create();
       ResponseEntity<ByteArrayResource> imageDtoResponseEntity =  restClient
                .get()
                .uri(httpRequestUtils.getImageServiceUri(imageId))
                .retrieve()
                .toEntity(ByteArrayResource.class);
        return imageDtoResponseEntity.getBody().getByteArray();

    }

    @Override
    public ImageDto save(MultipartFile fileContent) throws IOException {
        RestClient restClient = RestClient.create();

        byte[] bytes = fileContent.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes){
            @Override
            public String getFilename(){
                return fileContent.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> map  = new LinkedMultiValueMap<>();
        map.add("file", byteArrayResource);
       ResponseEntity<ImageDto> imageDtoResponseEntity = restClient
                .post()
                .uri(httpRequestUtils.getImageServiceUri())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(map)
                .retrieve()
                .toEntity(ImageDto.class);

        return imageDtoResponseEntity.getBody();

        }



    @Override
    public void delete(String imageId) {
        RestClient restClient = RestClient.create();
         restClient
                .get()
                .uri(httpRequestUtils.getImageServiceUri(imageId))
                .retrieve()
                .toEntity(String.class);


    }

}

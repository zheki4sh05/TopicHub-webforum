package web.forum.topichub.services.impls;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;
import org.springframework.web.reactive.function.*;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.io.*;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService implements IImageService {

    private final UserRepository authDao;
    private final HttpRequestUtils httpRequestUtils;

    @Override
    public byte[] fetch(String userId) {
//            return fileStorage.findByPath(userId).orElseThrow(InternalServerErrorException::new);
        return null;

    }

    @Override
    public void save(String userId, MultipartFile fileContent) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", fileContent.getResource());
            Flux<ImageDto> imageFlux = WebClient.create()
                    .post()
                    .uri(httpRequestUtils.getImageServiceUtl())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToFlux(ImageDto.class);
            imageFlux.subscribe(imageDto -> log.info(imageDto.getFilename()));

        }
}

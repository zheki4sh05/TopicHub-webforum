package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.client.ImageDto;
import web.forum.topichub.redis.ImageCache;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:52+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDto toDto(ImageCache imageCache) {
        if ( imageCache == null ) {
            return null;
        }

        ImageDto.ImageDtoBuilder imageDto = ImageDto.builder();

        imageDto.id( imageCache.getId() );
        imageDto.filename( imageCache.getFilename() );
        imageDto.uploadDate( imageCache.getUploadDate() );
        imageDto.imageSize( imageCache.getImageSize() );
        imageDto.targetId( imageCache.getTargetId() );
        imageDto.contentType( imageCache.getContentType() );
        imageDto.metaName( imageCache.getMetaName() );

        return imageDto.build();
    }

    @Override
    public ImageCache fromDto(ImageDto imageDto) {
        if ( imageDto == null ) {
            return null;
        }

        ImageCache.ImageCacheBuilder imageCache = ImageCache.builder();

        imageCache.id( imageDto.getId() );
        imageCache.filename( imageDto.getFilename() );
        imageCache.uploadDate( imageDto.getUploadDate() );
        imageCache.imageSize( imageDto.getImageSize() );
        imageCache.targetId( imageDto.getTargetId() );
        imageCache.contentType( imageDto.getContentType() );
        imageCache.metaName( imageDto.getMetaName() );

        return imageCache.build();
    }
}

package web.forum.topichub.mapper;

import org.mapstruct.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.redis.*;
import web.forum.topichub.util.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

    @Mappings({
              @Mapping(target = "id", source = "id"),
            @Mapping(target = "filename", source = "filename"),
            @Mapping(target = "uploadDate", source = "uploadDate"),
            @Mapping(target = "imageSize", source = "imageSize"),
            @Mapping(target = "targetId", source = "targetId"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "metaName", source = "metaName"),
            })
    ImageDto toDto(ImageCache imageCache);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "filename", source = "filename"),
            @Mapping(target = "uploadDate", source = "uploadDate"),
            @Mapping(target = "imageSize", source = "imageSize"),
            @Mapping(target = "targetId", source = "targetId"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "metaName", source = "metaName"),
    })
    ImageCache fromDto(ImageDto imageDto);
}

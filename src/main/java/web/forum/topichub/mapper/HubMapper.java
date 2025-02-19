package web.forum.topichub.mapper;


import org.mapstruct.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.model.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HubMapper {

    @Mapping(target = "id", expression = "java(hub.getId().toString())")
    @Mapping(target = "en", source = "enName")
    @Mapping(target = "ru", source = "ruName")
    HubDto toDto(Hub hub);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ruName", source = "ru")
    @Mapping(target = "enName", source = "en")
    Hub fromDto(HubDto hubDto);
}

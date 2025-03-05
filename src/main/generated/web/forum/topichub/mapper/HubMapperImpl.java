package web.forum.topichub.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.forum.topichub.dto.HubDto;
import web.forum.topichub.model.Hub;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T22:21:52+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class HubMapperImpl implements HubMapper {

    @Override
    public HubDto toDto(Hub hub) {
        if ( hub == null ) {
            return null;
        }

        HubDto.HubDtoBuilder hubDto = HubDto.builder();

        hubDto.en( hub.getEnName() );
        hubDto.ru( hub.getRuName() );

        hubDto.id( hub.getId().toString() );

        return hubDto.build();
    }

    @Override
    public Hub fromDto(HubDto hubDto) {
        if ( hubDto == null ) {
            return null;
        }

        Hub.HubBuilder hub = Hub.builder();

        hub.ruName( hubDto.getRu() );
        hub.enName( hubDto.getEn() );

        return hub.build();
    }
}

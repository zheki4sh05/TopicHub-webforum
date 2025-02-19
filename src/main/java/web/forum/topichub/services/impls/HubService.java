package web.forum.topichub.services.impls;

import lombok.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;
import java.util.stream.*;
@Service
@AllArgsConstructor
public class HubService implements IHubService {

    private final HubRepository hubRepository;
    private final HubMapper hubMapper;
    @Override
    public List<HubDto> findAll() {
        var list = hubRepository.findAll();
        return  list.stream()
                .map(hubMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public HubDto create(HubDto hubDto) {
        Hub hub = hubMapper.fromDto(hubDto);
       Hub created = hubRepository.save(hub);
        return hubMapper.toDto(created);
    }

    @Override
    public void delete(Long hubId) {
        Hub hub = hubRepository.findById(hubId.intValue()).orElseThrow(EntityNotFoundException::new);
        hubRepository.delete(hub);
    }

    @Override
    public HubDto update(HubDto hubDto) {
        Hub hub = hubRepository.findById(Integer.parseInt(hubDto.getId())).orElseThrow(EntityNotFoundException::new);
        hub.setEnName(hubDto.getEn());
        hub.setRuName(hubDto.getRu());
        hubRepository.save(hub);
        return hubDto;
    }
}

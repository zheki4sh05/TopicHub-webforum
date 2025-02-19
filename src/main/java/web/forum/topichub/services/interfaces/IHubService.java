package web.forum.topichub.services.interfaces;

import web.forum.topichub.dto.*;

import java.util.*;

public interface IHubService {
    List<HubDto> findAll();

    HubDto create(HubDto hubDto);

    void delete(Long hubId);

    HubDto update(HubDto hubDto);
}

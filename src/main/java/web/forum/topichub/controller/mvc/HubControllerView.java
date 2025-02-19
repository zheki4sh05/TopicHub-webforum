package web.forum.topichub.controller.mvc;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;


@Controller
@RequestMapping("/admin/hub")
@AllArgsConstructor
public class HubControllerView {

    private final IHubService hubService;
    private final HttpRequestUtils httpRequestUtils;

    @GetMapping("/fetch")
    public String fetchAll(Model model){
        var list = hubService.findAll();
        model.addAttribute("hubs", list);
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/hub/main";
    }
    @GetMapping("/create")
    public String edit(){
        return "admin/hub/create-hub";
    }

    @GetMapping("/edit")
    public String edit(
            @RequestParam("id") String id,
            @RequestParam("en") String en,
            @RequestParam("ru") String ru,
            Model model){
        HubDto hubDto = HubDto.builder()
                .id(id)
                .ru(ru)
                .en(en)
                .build();
        model.addAttribute("hub", hubDto);
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/hub/update-hub";
    }

}

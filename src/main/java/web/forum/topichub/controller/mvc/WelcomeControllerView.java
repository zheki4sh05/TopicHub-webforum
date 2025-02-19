package web.forum.topichub.controller.mvc;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.util.*;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class WelcomeControllerView {
    private final HttpRequestUtils httpRequestUtils;
    @GetMapping("")
    public String welcome(Model model){
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/index";
    }
}
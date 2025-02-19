package web.forum.topichub.controller.mvc;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.util.*;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class HelloPageController {

    private final HttpRequestUtils httpRequestUtils;

    @GetMapping("")
    public String hello(Model model){
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        model.addAttribute("adminLink","/auth");
        model.addAttribute("sourceLink","https://github.com/zheki4sh05/TopicHub-web-forum");
        return "index";
    }
}

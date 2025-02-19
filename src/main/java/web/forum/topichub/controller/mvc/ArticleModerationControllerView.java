package web.forum.topichub.controller.mvc;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.util.*;


@Controller
@RequestMapping("/admin/article")
@AllArgsConstructor
@Slf4j
public class ArticleModerationControllerView {

    private final IArticleService articleService;
    private final IHubService hubService;
    private final CustomSecurityExpression customSecurityExpression;
    private final HttpRequestUtils httpRequestUtils;
    @GetMapping("/fetch")
    public String getModeration(
            @RequestParam Map<String, String> reqParam,
            Model model
            ){
        var articleFilter = HttpRequestUtils.parseFilterParams(reqParam);
        articleFilter.setUserId(customSecurityExpression.getUserId());
        PageResponse<ArticleDto> articles = articleService.fetch(articleFilter);
        model.addAttribute("batch", articles);
        model.addAttribute("status", articleFilter.getStatus());
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/article/index";
    }
    @GetMapping("/view")
    public String getModeration(
            @ModelAttribute("articleStatus") ArticleStatusDto articleStatusDto,
            Model model
    ){
        ArticleDto articleDto = articleService.findById(articleStatusDto.getId());
        List<HubDto> hubDtos = hubService.findAll();
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        model.addAttribute("article", articleDto);
        model.addAttribute("page", articleStatusDto.getPage());
        model.addAttribute("hub", hubDtos.stream()

                .filter(item->item.getId().equals(articleDto.getHub().toString()
                )).findFirst().orElse(HubDto.builder().id("0").build()));

        String status;
        if(articleStatusDto.getStatus().isEmpty()){
            status = articleService.getStatusNameById(articleStatusDto.getId());
        }else{
            status = articleStatusDto.getStatus();
        }
        model.addAttribute("status", status);
        model.addAttribute("page", articleStatusDto.getPage());
        return "admin/article/overview";
    }
}

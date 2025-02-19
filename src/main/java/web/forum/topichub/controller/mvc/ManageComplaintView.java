package web.forum.topichub.controller.mvc;


import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;


@Controller
@RequestMapping("/admin/complaint")
@AllArgsConstructor
@Slf4j
public class ManageComplaintView {

    private final IComplaintControl complaintControl;
    private final HttpRequestUtils httpRequestUtils;
    @GetMapping("/fetch/{type}")
    public String fetchComplaints(
            @PathVariable(value = "type") String type,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            Model model
    ){
        PageResponse<ComplaintDto> complaintDtoPageResponse =complaintControl
                .findAllByType(type, PageRequest.of(page-1, 15));
        model.addAttribute("list", complaintDtoPageResponse.getItems());
        model.addAttribute("page",complaintDtoPageResponse.getPage());
        model.addAttribute("total",complaintDtoPageResponse.getMaxPage());
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        model.addAttribute("type", type);
     return "admin/complaint/index";
    }
    @GetMapping("/view/{type}")
    public String getComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "number") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "id") String id,
            Model model
    ){
        ComplaintDto complaintDto = complaintControl.findById(id,type);
        model.addAttribute("complaint", complaintDto);
        model.addAttribute("page", page);
        model.addAttribute("total",size);
        model.addAttribute("type", type);
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return "admin/complaint/overview";
    }
    @PostMapping ("/del/{type}")
    public String delComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "number") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "id") String id,
            Model model
    ){
        complaintControl.deleteById(id,type);
        model.addAttribute("page", page);
        model.addAttribute("total",size);
        model.addAttribute("type", type);
        model.addAttribute("returnLink",httpRequestUtils.getClientUrl());
        return back(page, type);
    }
    private String back(Integer page, String type){
        return "redirect:"+ UriComponentsBuilder
                .fromUriString("/admin/article/fetch/"+type)
                .queryParam("page", page)
                .toUriString();
    }
}

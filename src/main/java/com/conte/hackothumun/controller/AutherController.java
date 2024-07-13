package com.conte.hackothumun.controller;

import com.conte.hackothumun.entity.Event;
import com.conte.hackothumun.service.EventServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class AutherController {

    private EventServiceImplementation eventServiceImplementation;

    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "4") int size,
            @RequestParam(name="keyword",defaultValue="") String kw
    ) {
        Page<Event> eventList = eventServiceImplementation.findAllEvents(page,size,kw);
        System.out.println(eventList.getTotalElements());
        model.addAttribute("listEvents",eventList.getContent());
        model.addAttribute("pages",new int[eventList.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "index";
    }
}

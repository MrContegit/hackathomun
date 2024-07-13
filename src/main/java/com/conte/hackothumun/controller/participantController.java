package com.conte.hackothumun.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class participantController {
    @GetMapping("/paticipant")
    public String participantRoute(Model model) {
        return "participant";
    }
}

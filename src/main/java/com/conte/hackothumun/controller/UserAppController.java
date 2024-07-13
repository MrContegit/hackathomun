package com.conte.hackothumun.controller;
import com.conte.hackothumun.entity.UserApp;
import com.conte.hackothumun.service.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;


@Controller
@AllArgsConstructor
public class UserAppController {
    private final AccountServiceImpl accountService;

    @GetMapping("/login")
    public String loginPage(Model model){
//        model.addAttribute("user", new UserApp());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new UserApp());
        return "registerPage";
    }
    @PostMapping("/showUser")
    public String showUser(UserApp user){
//      accountService.loadUserByUsername(user.getUsername());
        User userApp = accountService.loadUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(userApp != null){
            return "redirect:/index";
        }else{
            return "redirect:/login";
        }

    }
    @PostMapping("/createUser")
    public String createUser(UserApp user, Model model){
        try {
            accountService.addNewUser(user);
            return "redirect:/index";
        }catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            return "index";
        }

    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }
}

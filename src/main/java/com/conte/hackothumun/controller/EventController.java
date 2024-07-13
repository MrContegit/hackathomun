package com.conte.hackothumun.controller;

import com.conte.hackothumun.entity.Billet;
import com.conte.hackothumun.entity.Event;
import com.conte.hackothumun.entity.FileApp;
import com.conte.hackothumun.entity.UserApp;
import com.conte.hackothumun.service.AccountServiceImpl;
import com.conte.hackothumun.service.BilletServiceImpl;
import com.conte.hackothumun.service.EventServiceImplementation;
import com.conte.hackothumun.service.FileStorageService;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class EventController {
    private EventServiceImplementation eventServiceImplementation;
    private FileStorageService fileStorageService;
    private BilletServiceImpl billetServiceImpl;
    private AccountServiceImpl accountServiceImpl;

    @GetMapping("/index")
    public String index(
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

    @GetMapping("/formEvents")
    public String formEvents(Model model){
        Event event = new Event();
        event.setPrivated(false);
        event.setApprove(false);
        UserDetails userDetails = accountServiceImpl.getCurrentUserDetails();
        List<Event> eventList = eventServiceImplementation.findAllEventsByorganizer(userDetails.getUsername());
        model.addAttribute("listEvents",eventList);
        model.addAttribute("event",event);
        model.addAttribute("billet", new Billet());
        return "formEvents";
    }

    @GetMapping("/oneEvent")
    public String oneEvent(Model model,@Valid Event event){
        model.addAttribute("event",event);
        return "oneEvent";
    }

    @PostMapping("/createEvent")
    public String createEvent(@Valid Event ev, MultipartFile[] files, @Valid Billet billet, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "formEvents";
        }
        try {
            if (eventServiceImplementation.eventExists(ev.getTitle())) {
                model.addAttribute("errorMessage", "Cet événement existe déjà");
                return "formEvents";
            }

            Event event = eventServiceImplementation.insert(ev);
            List<FileApp> fileAppList = new ArrayList<>();

            for (MultipartFile file : files) {
                FileApp fileApp = fileStorageService.storeFile(file, event);
                fileAppList.add(fileApp);
            }

            // Vérifiez l'objet billet avant de l'insérer
            if (billet == null) {
                model.addAttribute("errorMessage", "Le billet ne doit pas être null");
                return "formEvents";
            }

            billet.setEvent(event);
            Billet savedBillet = billetServiceImpl.insert(billet);

            if (savedBillet.getId() == null) {
                model.addAttribute("errorMessage", "Le billet n'a pas pu être créé");
                return "formEvents";
            }

            event.setFilePathList(fileAppList);
            eventServiceImplementation.update(event);
            return "redirect:/index";
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur s'est produite lors de la création de l'événement");
            return "formEvents";
        }
    }

    @DeleteMapping("/deleteEvent")
    public void deleteEvent(@RequestParam(name="id",required=true) Long id) {
        eventServiceImplementation.delete(id);
    }
}

package com.conte.hackothumun.service;

import com.conte.hackothumun.entity.Event;
import com.conte.hackothumun.entity.Role;
import com.conte.hackothumun.entity.UserApp;
import com.conte.hackothumun.repository.EventRepo;
import com.conte.hackothumun.repository.RoleRepo;
import com.conte.hackothumun.repository.UserAppRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;

@Service
@Transactional
@AllArgsConstructor
public class EventServiceImplementation implements ServiceCRUD<Event> {
    private EventRepo eventRepo;
    private AccountServiceImpl accountService;
    private UserAppRepo userAppRepo;

    public Page<Event> findAllEvents(int page, int size, String kw) {
        return eventRepo.findByTitleContaining(kw, PageRequest.of(page, size));
    }
    public List<Event> findAllEventsByorganizer(String email) {
        return eventRepo.findByOrganizerId(email);
    }
    @Override
    public Event select(Long id) {
        return null;
    }
    public List<UserApp> selectAllVisitor(Long id) {
        return eventRepo.findInviteByEvent(id.toString());
    }

    @Override
    public Event insert(Event value) {
        Event event = eventRepo.findByTitle(value.getTitle());
        if(event != null) throw new RuntimeException("This event already exists");
        UserDetails userDetails = accountService.getCurrentUserDetails();
        UserApp userApp = userAppRepo.findByEmail(userDetails.getUsername());
        if(userApp !=null){
            value.setOrganizerId(userApp);
            System.out.println("///////////////////////////////////////////////////////"+value.getOrganizerId());
        }else{
            System.out.println("////////////////////nuuuuuuuuuuuu///////////n/llllllllllll//////////");
        }
        return eventRepo.save(value);
    }

    @Override
    public Event update(Event value) {

        return eventRepo.save(value);
    }

    @Override
    public void delete(Long value) {
        Event event = eventRepo.findById(value).orElse(null);
        if(event == null) throw new RuntimeException("This event don't exists");
        eventRepo.deleteById(value);
    }

    public boolean eventExists(String title) {
        return eventRepo.findByTitle(title) != null;
    }

}

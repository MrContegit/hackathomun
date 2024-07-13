package com.conte.hackothumun.service;

import com.conte.hackothumun.entity.Role;
import com.conte.hackothumun.entity.UserApp;
import com.conte.hackothumun.repository.RoleRepo;
import com.conte.hackothumun.repository.UserAppRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService,UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private UserAppRepo userAppRepo;
    private RoleRepo roleRepo;


    @Override
    public UserApp addNewUser(UserApp user) {
        if (userAppRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("This User already exists");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match    "+user.getPassword()+" "+user.getConfirmPassword());
        }
        Role role = roleRepo.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        UserApp newUser = UserApp.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(roles)
                .build();
        return userAppRepo.save(newUser);
    }

    @Override
    public Role addNewRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToUser(Long id, Role role) {
        UserApp appUser = userAppRepo.findById(id).get();
        appUser.getRoles().add(role);
        userAppRepo.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        UserApp appUser = userAppRepo.findByUsername(username);
        appUser.getRoles().remove(role);
        userAppRepo.save(appUser);
    }

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("Utilisateur connecté : " + userDetails);
            return userDetails;
        }else{
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+email);

        try {
            UserApp user = userAppRepo.findByEmail(email);

            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+user.getUsername());

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++user not found");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+e.getMessage());

        }

        return null;
    }

    public User loadUserByEmailAndPassword(String email,String password) throws UsernameNotFoundException {
        UserApp user = userAppRepo.findByEmail(email);

        if(user == null){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++user not found");
            throw new UsernameNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("////////////////////////////////////////////////////////////user not found");
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public UserApp loadUserByUsernameAndPassword(String username,String password) throws UsernameNotFoundException {
        return userAppRepo.findByUsernameAndPassword(username, password);
    }
}

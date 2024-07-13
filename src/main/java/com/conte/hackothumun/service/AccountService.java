package com.conte.hackothumun.service;

import com.conte.hackothumun.entity.Role;
import com.conte.hackothumun.entity.UserApp;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {
    UserApp addNewUser(UserApp user);
    Role addNewRole(Role role);
    void addRoleToUser(Long id, Role role);
    void removeRoleFromUser(String username, String role);
    UserDetails loadUserByUsername(String email);
}

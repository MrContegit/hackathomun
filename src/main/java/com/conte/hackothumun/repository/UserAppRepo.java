package com.conte.hackothumun.repository;

import com.conte.hackothumun.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepo extends JpaRepository<UserApp, Long> {
    UserApp findByUsername(String name);
    UserApp findByEmailAndPassword(String email, String password);
    UserApp findByUsernameAndPassword(String username, String password);
    UserApp findByEmail(String email);
}

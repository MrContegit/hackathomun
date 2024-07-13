//package com.conte.hackothumun.config.security;
//
//import com.conte.hackothumun.entity.Role;
//import com.conte.hackothumun.repository.RoleRepo;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner initRoles(RoleRepo roleRepo) {
//        return args -> {
//            // Créer et enregistrer les rôles
//            Role adminRole = new Role();
//            adminRole.setName("ROLE_ADMIN");
//            roleRepo.save(adminRole);
//
//            Role userRole = new Role();
//            userRole.setName("ROLE_USER");
//            roleRepo.save(userRole);
//
//            Role modRole = new Role();
//            modRole.setName("ROLE_MODERATOR");
//            roleRepo.save(modRole);
//
//            System.out.println("Rôles initialisés avec succès !");
//        };
//    }
//}

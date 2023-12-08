package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;

@Component
public class DatabaseInit {
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInit(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void Init() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        HashSet<Role> userRoles = new HashSet<>();
        HashSet<Role> adminRoles = new HashSet<>();

        userRoles.add(userRole);
        adminRoles.add(userRole);
        adminRoles.add(adminRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setName("da");
        admin.setSurname("da");
        admin.setRoles(adminRoles);


        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123"));
        user.setName("ta");
        user.setSurname("ta");
        user.setRoles(userRoles);

        roleRepo.save(adminRole);
        roleRepo.save(userRole);
        userRepo.save(admin);
        userRepo.save(user);
    }
}

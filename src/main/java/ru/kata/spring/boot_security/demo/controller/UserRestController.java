package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Value("${fetch.url}")
    private String URL;


    @GetMapping(value = "/admin")
    public ModelAndView adminPage(ModelMap model
            , Principal principal
    ) {
        ModelAndView admin = new ModelAndView("userlist");
        model.addAttribute("logged_user", userService.getByUsername(principal.getName()));
        model.addAttribute("adminApiURL",URL);
        return admin;
    }

    @GetMapping(value = "/user")
    public  ModelAndView user(ModelMap model, Principal principal) {
        ModelAndView user = new ModelAndView("user");
        model.addAttribute("logged_user", userService.getByUsername(principal.getName()));
        return user;
    }
    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value = "/admin/user")
    public ResponseEntity<User> getUserById(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }
    @DeleteMapping(value = "/admin/users")
    public ResponseEntity<String> deleteUser(@RequestParam(name = "id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/admin/users")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/users")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void setRolesToUser(int roleid, User user, int opid) {
        Role userRole = roleService.getById(2);
        Role adminRole = roleService.getById(1);

        HashSet<Role> userRoles = new HashSet<>();
        HashSet<Role> adminRoles = new HashSet<>();

        userRoles.add(userRole);
        adminRoles.add(adminRole);
        if (roleid == 1) {
            user.setRoles(adminRoles);
        }
        if (roleid == 2) {
            user.setRoles(userRoles);
        }
        if (roleid == 0 && opid == 1) {
            user.setRoles(userService.findById(user.getId()).getRoles());
        }
    }
}

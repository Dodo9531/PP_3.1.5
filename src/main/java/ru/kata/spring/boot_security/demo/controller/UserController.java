//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//import ru.kata.spring.boot_security.demo.services.UserService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Controller
//public class UserController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleService roleService;
//
//    @GetMapping(value = "/")
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping(value = "/admin")
//    public String users(ModelMap model,Principal principal) {
//            List<User> user = userService.getAllUsers();
//            model.addAttribute("users", user);
//            model.addAttribute("logged_user", userService.getByUsername(principal.getName()));
//            model.addAttribute("user", new User());
//            model.addAttribute("update_user",new User());
//            model.addAttribute("delete_user", new User());
//            return "/userlist.html";
//
//    }
//    @GetMapping(value = "/user")
//    public String user(ModelMap model, Principal principal) {
//        model.addAttribute("logged_user", userService.getByUsername(principal.getName()));
//        return "/user.html";
//    }
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }
//    @PostMapping(value = "/admin/users/add")
//    public String addUser(@ModelAttribute("user") User user,@RequestParam("roles") int roleid) {
//        setRolesToUser(roleid, user,0);
//        userService.addUser(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping(value = "/admin/users/delete")
//    public String addUser(int id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
//
//    @PostMapping(value = "/admin/users/update")
//    public String update_user(@RequestParam("updateroles") int roleid,User user) {
//        setRolesToUser(roleid, user,1);
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    private void setRolesToUser(int roleid, User user, int opid) {
//        Role userRole = roleService.getById(2);
//        Role adminRole = roleService.getById(1);
//
//        HashSet<Role> userRoles = new HashSet<>();
//        HashSet<Role> adminRoles = new HashSet<>();
//
//        userRoles.add(userRole);
//        adminRoles.add(adminRole);
//        if(roleid == 1) {
//            user.setRoles(adminRoles);
//        }
//        if(roleid == 2) {
//            user.setRoles(userRoles);
//        }
//        if(roleid == 0 && opid == 1) {
//            user.setRoles(userService.getById(user.getId()).getRoles());
//        }
//    }
//}

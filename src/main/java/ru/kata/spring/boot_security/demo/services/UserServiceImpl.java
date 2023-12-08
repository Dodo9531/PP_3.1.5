package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setBeans(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepo.findById(id).get();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        User userToUpdate = userRepo.getById(user.getId());
        if (user.getName() != "") {
            userToUpdate.setName(user.getName());
        }
        if (user.getSurname() != "") {
            userToUpdate.setSurname(user.getSurname());
        }
        if (user.getPassword() != "") {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getUsername() != "") {
            userToUpdate.setUsername(user.getUsername());
        }
        if(!user.getRoles().isEmpty()) {
            userToUpdate.setRoles(user.getRoles());
        }
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }

}

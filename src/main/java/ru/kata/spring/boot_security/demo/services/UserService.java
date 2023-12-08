package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User findById(int id);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(int id);
    public User getByUsername(String username);
}

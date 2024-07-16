package com.ficbic.dpeq.service;


import com.ficbic.dpeq.pojo.User;

import java.util.List;

public interface UserService {
    void createUser(String username, String email, String password);
    List<User> getAllUsers();
    User getUser(Long userId);
    User getUser(String userId);
    void deleteUser(Long userId);
    void updateUser(User user);
}

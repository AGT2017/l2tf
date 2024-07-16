package com.ficbic.dpeq.service;

import com.ficbic.dpeq.mapper.UserMapper;
import com.ficbic.dpeq.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultUserService implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getCurrentUser() {
        final Long currentUserId = (Long) SecurityUtils.getSubject().getPrincipal();
        if (currentUserId != null) {
            return getUser(currentUserId);
        } else {
            return null;
        }
    }

    public void createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(new Sha256Hash(password).toHex());
        userMapper.createUser(user);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User getUser(Long userId) {
        return userMapper.getUserById(userId);
    }

    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public User getUser(String username) {
        return userMapper.getUserByName(username);
    }
}

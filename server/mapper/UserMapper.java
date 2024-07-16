package com.ficbic.dpeq.mapper;

import com.ficbic.dpeq.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(@Param("id") long userId);
    User getUserByName(String username);
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(Long userId);
    void updateUser(User user);
}

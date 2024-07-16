package com.ficbic.dpeq.mapper;

import com.ficbic.dpeq.pojo.Role;
import com.ficbic.dpeq.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    Role getRole(@Param("name") String name);
    List<Role> getRoles();
}

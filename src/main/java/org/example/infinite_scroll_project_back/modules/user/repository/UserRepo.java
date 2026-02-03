package org.example.infinite_scroll_project_back.modules.user.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.infinite_scroll_project_back.modules.user.model.User;

import java.util.Optional;

@Mapper
public interface UserRepo {
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    // 1. Find user by email or username
    @Select("SELECT * FROM users WHERE email = #{identifier} OR username = #{identifier}")
    Optional<User> findByEmailOrUsername(String identifier);

    // 2. Register new user
    @Insert("INSERT INTO users(email, username, password) VALUES(#{email}, #{username}, #{password})")
    void save(User user);
}
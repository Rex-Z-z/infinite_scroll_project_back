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

    @Insert("INSERT INTO users(email, password) VALUES(#{email}, #{password})")
    void save(User user);
}
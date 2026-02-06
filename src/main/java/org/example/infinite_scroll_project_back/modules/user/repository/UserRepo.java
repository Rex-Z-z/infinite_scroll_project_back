package org.example.infinite_scroll_project_back.modules.user.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.infinite_scroll_project_back.modules.user.model.User;

import java.util.Optional;

@Mapper
public interface UserRepo {
    // 1. Find user by email
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    // 2. Find user by username
    @Select("SELECT * FROM users WHERE email = #{identifier} OR username = #{identifier}")
    Optional<User> findByEmailOrUsername(String identifier);

    // 3. Save new user
    @Insert("INSERT INTO users(email, username, password) VALUES(#{email}, #{username}, #{password})")
    void save(User user);

    // 4. Update username
    @Update("UPDATE users SET username = #{newUsername} WHERE username = #{oldUsername}")
    void updateUsername(String oldUsername, String newUsername);

    // 5. Update password
    @Update("UPDATE users SET password = #{newPassword} WHERE email = #{email}")
    void updatePassword(String email, String newPassword);

    // 6. Update profile image URL
    @Update("UPDATE users SET profile_image_url = #{imageUrl} WHERE username = #{username}")
    void updateProfileImage(String username, String imageUrl);
}
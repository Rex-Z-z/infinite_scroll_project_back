package org.example.infinite_scroll_project_back.modules.user.service;

import org.example.infinite_scroll_project_back.modules.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    boolean userExists(String email);

    // Add your own custom methods here
    void registerUser(User user);
    void changeUsername(String currentUsername, String newUsername);
    void resetPassword(String email, String newPassword);
    String uploadProfileImage(String username, MultipartFile file);
}
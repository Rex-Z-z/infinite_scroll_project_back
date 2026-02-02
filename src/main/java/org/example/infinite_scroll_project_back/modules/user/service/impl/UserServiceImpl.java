package org.example.infinite_scroll_project_back.modules.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.user.model.User;
import org.example.infinite_scroll_project_back.modules.user.repository.UserRepo;
import org.example.infinite_scroll_project_back.modules.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // 1. This method is required by Spring Security for Login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // 2. This is your custom method for Registration
    @Override
    public void registerUser(User user) {
        // Always hash the password before saving!
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);
    }
}
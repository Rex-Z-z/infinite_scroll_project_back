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

    // 1. Load user by email OR username
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Search by email OR username
        return userRepo.findByEmailOrUsername(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + identifier));
    }

    // 2. Register new user
    @Override
    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

    // 3. Check if user exists by email
    @Override
    public boolean userExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}
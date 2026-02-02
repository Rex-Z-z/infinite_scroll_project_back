package org.example.infinite_scroll_project_back.modules.user.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {
    private Long id;
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
    @Override
    public String getUsername() { return email; }
    // Return true for the boolean flags (isAccountNonExpired, etc.)
}
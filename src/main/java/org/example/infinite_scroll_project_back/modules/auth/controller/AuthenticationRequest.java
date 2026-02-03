package org.example.infinite_scroll_project_back.modules.auth.controller;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String username;
    private String password;
}
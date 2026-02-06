package org.example.infinite_scroll_project_back.modules.auth.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
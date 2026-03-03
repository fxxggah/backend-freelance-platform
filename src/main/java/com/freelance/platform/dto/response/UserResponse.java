package com.freelance.platform.dto.response;

import com.freelance.platform.domain.enums.UserType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserType userType;
    private LocalDateTime createdAt;
}
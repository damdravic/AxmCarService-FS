package com.example.AxmCarService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @NotNull
    private Long userId;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "LAst name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email name cannot be empty")
    @Email(message = "Invalid Email.Please enter a valid mail address")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;





}

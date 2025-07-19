package com.example.Sale_Campaign_Management_System.DTOs;

import com.example.Sale_Campaign_Management_System.Model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid India phone Number(must be start with 6-9 and be 10 Digits)"
    )
    private String phoneNumber;

    @NotBlank
    private String city;


    @NotBlank
    private String password;

    private Role role;
}

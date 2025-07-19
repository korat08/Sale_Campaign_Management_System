package com.example.Sale_Campaign_Management_System.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userName",unique = true,nullable = false)
    private String userName;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email Format")
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull(message = "Phone Number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid India phone Number(must be start with 6-9 and be 10 Digits)"
    )
    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

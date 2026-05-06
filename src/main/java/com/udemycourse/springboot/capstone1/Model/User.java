package com.udemycourse.springboot.capstone1.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "UserName cannot be empty")
    @Size(min = 6 , message = "UserName must be more than 5 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$", message = "Password must contain letters and digits")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    @Pattern(regexp = "(?i)^(Admin|Customer)$", message = "Role must be Admin or Customer")
    private String role;

    @NotNull(message = "Balance cannot be Null")
    @Positive(message = "Balance must be positive")
    private double balance;

}

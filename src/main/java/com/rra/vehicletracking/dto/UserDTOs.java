package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public class UserDTOs {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  static  class SignupRequest{
        @NotBlank(message = "Names are required")
        @Size(min=3, max=100, message = "Names must be between 3 and 100 characters")
        private  String names;

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        private  String phone;

        @NotBlank(message = "National id is required")
        @Size(min=16, max=16, message = "National ID must be 16 characters")
        private  String nationalId;

        @NotBlank(message = "Password is required")
        @Size(min=8, message = "Password must be at least 8 characters")
        private String password;

        @NotBlank(message = "Address is required")
        private String address;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class LoginRequest{
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  static  class UserResponse{
        private Long id;
        private String names;
        private String email;
        private String phone;
        private String nationalId;
        private String address;
        private Set<String> roles;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtResponse{
        private  String token;
        private  String type="Bearer";
        private Long id;
        private String email;
        private Set<String> roles;
    }
}

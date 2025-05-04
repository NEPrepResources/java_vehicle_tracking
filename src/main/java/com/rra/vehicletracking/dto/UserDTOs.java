package com.rra.vehicletracking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

public class UserDTOs {

    public static class SignupRequest {
        @NotBlank(message = "Names are required")
        @Size(min=3, max=100, message = "Names must be between 3 and 100 characters")
        private String names;

        @NotBlank(message = "Email is required")
        @Size(min=3, max=100, message = "Email must be between 3 and 100 characters")
        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        private String phone;

        @NotBlank(message = "National id is required")
        @Size(min=16, max=16, message = "National ID must be 16 characters")
        private String nationalId;

        @NotBlank(message = "Password is required")
        @Size(min=8, message = "Password must be at least 8 characters")
        private String password;

        @NotBlank(message = "Address is required")
        private String address;

        private String role;

        public SignupRequest() {
        }

        public SignupRequest(String names, String email, String phone, String nationalId,
                             String password, String address, String role) {
            this.names = names;
            this.email = email;
            this.phone = phone;
            this.nationalId = nationalId;
            this.password = password;
            this.address = address;
            this.role = role;
        }

        // Getters and Setters
        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        public LoginRequest() {
        }

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class UserResponse {
        private Long id;
        private String names;
        private String email;
        private String phone;
        private String nationalId;
        private String address;
        private Set<String> roles;

        public UserResponse() {
        }

        public UserResponse(Long id, String names, String email, String phone,
                            String nationalId, String address, Set<String> roles) {
            this.id = id;
            this.names = names;
            this.email = email;
            this.phone = phone;
            this.nationalId = nationalId;
            this.address = address;
            this.roles = roles;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }
    }

    public static class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String username;
        private String email;
        private Set<String> roles;

        public JwtResponse() {
        }

        public JwtResponse(String token, String type, Long id, String username,
                           String email, Set<String> roles) {
            this.token = token;
            this.type = type;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }

        // Getters and Setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }
    }
}
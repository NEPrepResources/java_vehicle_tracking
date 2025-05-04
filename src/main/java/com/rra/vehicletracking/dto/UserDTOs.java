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
    public  static  class SignupRequest{
        @NotBlank(message = "Names are required")
        @Size(min=3, max=100, message = "Names must be between 3 and 100 characters")
        private  String names;

        @NotBlank(message = "Email is required")
        @Size(min=3, max=100, message = "Email must be between 3 and 100 characters")
        private  String email;

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

        private String role;

        public SignupRequest() {
        }

        public SignupRequest(String names, String email, String phone, String nationalId, String password, String address, String role) {
            this.names = names;
            this.email = email;
            this.phone = phone;
            this.nationalId = nationalId;
            this.password = password;
            this.address = address;
            this.role = role;
        }

        public @NotBlank(message = "Names are required") @Size(min = 3, max = 100, message = "Names must be between 3 and 100 characters") String getNames() {
            return names;
        }

        public void setNames(@NotBlank(message = "Names are required") @Size(min = 3, max = 100, message = "Names must be between 3 and 100 characters") String names) {
            this.names = names;
        }

        public @NotBlank(message = "Email is required") @Size(min = 3, max = 100, message = "Email must be between 3 and 100 characters") String getEmail() {
            return email;
        }

        public void setEmail(@NotBlank(message = "Email is required") @Size(min = 3, max = 100, message = "Email must be between 3 and 100 characters") String email) {
            this.email = email;
        }

        public @NotBlank(message = "Phone is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid") String getPhone() {
            return phone;
        }

        public void setPhone(@NotBlank(message = "Phone is required") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid") String phone) {
            this.phone = phone;
        }

        public @NotBlank(message = "National id is required") @Size(min = 16, max = 16, message = "National ID must be 16 characters") String getNationalId() {
            return nationalId;
        }

        public void setNationalId(@NotBlank(message = "National id is required") @Size(min = 16, max = 16, message = "National ID must be 16 characters") String nationalId) {
            this.nationalId = nationalId;
        }

        public @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters") String getPassword() {
            return password;
        }

        public void setPassword(@NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters") String password) {
            this.password = password;
        }

        public @NotBlank(message = "Address is required") String getAddress() {
            return address;
        }

        public void setAddress(@NotBlank(message = "Address is required") String address) {
            this.address = address;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class LoginRequest{
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        public @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String getEmail() {
            return email;
        }

        public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
            this.email = email;
        }

        public @NotBlank(message = "Password is required") String getPassword() {
            return password;
        }

        public void setPassword(@NotBlank(message = "Password is required") String password) {
            this.password = password;
        }
    }

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtResponse{
        private  String token;
        private  String type="Bearer";
        private Long id;
        private String email;
        private String username;
        private Set<String> roles;

        public JwtResponse() {
        }

        public JwtResponse(Set<String> roles, String username, String email, Long id, String type, String token) {
            this.roles = roles;
            this.username = username;
            this.email = email;
            this.id = id;
            this.type = type;
            this.token = token;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }
    }
}

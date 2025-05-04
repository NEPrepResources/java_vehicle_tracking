package com.rra.vehicletracking.service;

import com.rra.vehicletracking.dto.UserDTOs.LoginRequest;
import com.rra.vehicletracking.dto.UserDTOs.SignupRequest;
import com.rra.vehicletracking.dto.UserDTOs.JwtResponse;
import com.rra.vehicletracking.entity.User;
import com.rra.vehicletracking.repository.UserRepository;
import com.rra.vehicletracking.security.JwtUtils;
import com.rra.vehicletracking.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return new JwtResponse(
                roles,
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getId(),
                "Bearer",
                jwt
        );
    }

    public User registerUser(SignupRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        if(userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Error: Phone is already in use!");
        }
        if(userRepository.existsByNationalID(request.getNationalId())) {
            throw new RuntimeException("Error: National ID is already in use!");
        }

        User user = new User();
        user.setNames(request.getNames());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setNationalID(request.getNationalId());
        user.setPassword(encoder.encode(request.getPassword())); // Encode the password here
        user.setAddress(request.getAddress());

        Set<String> roles = new HashSet<>();
        if(request.getRole() != null && request.getRole().equals("admin")) {
            roles.add("ROLE_ADMIN");
        } else {
            roles.add("ROLE_USER");
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }
}

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse authenticateUser(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().trim().toLowerCase(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            return new JwtResponse(
                    jwt,                       // token
                    "Bearer",                  // type
                    userDetails.getId(),       // id
                    userDetails.getUsername(), // username
                    userDetails.getEmail(),    // email
                    roles                     // roles
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public User registerUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        if (userRepository.existsByPhone(request.getPhone().trim())) {
            throw new RuntimeException("Error: Phone is already in use!");
        }
        if (userRepository.existsByNationalID(request.getNationalId().trim())) {
            throw new RuntimeException("Error: National ID is already in use!");
        }

        User user = new User();
        user.setNames(request.getNames().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhone(request.getPhone().trim());
        user.setNationalID(request.getNationalId().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setAddress(request.getAddress().trim());

        Set<String> roles = new HashSet<>();
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("admin")) {
            roles.add("ROLE_ADMIN");
        } else {
            roles.add("ROLE_USER");
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
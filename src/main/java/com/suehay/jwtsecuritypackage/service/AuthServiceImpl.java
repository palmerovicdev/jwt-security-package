package com.suehay.jwtsecuritypackage.service;

import com.suehay.jwtsecuritypackage.config.JwtService;
import com.suehay.jwtsecuritypackage.model.entity.User;
import com.suehay.jwtsecuritypackage.model.enums.Role;
import com.suehay.jwtsecuritypackage.model.request.AuthenticationRequest;
import com.suehay.jwtsecuritypackage.model.response.AuthenticationResponse;
import com.suehay.jwtsecuritypackage.model.response.GenericResponse;
import com.suehay.jwtsecuritypackage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GenericResponse<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        var passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.uasername(), authenticationRequest.password());
        authenticationManager.authenticate(passwordAuthenticationToken);

        var user = userRepository.findByUsername(authenticationRequest.uasername());

        return user.map(value -> new GenericResponse<>(null, "Login successful", "200", AuthenticationResponse.builder()
                                                                                                              .token(jwtService.generateToken(value, generateExtraClaims(value)))
                                                                                                              .username(value.getUsername())
                                                                                                              .role(value.getRole().name())
                                                                                                              .build())).orElseGet(() -> new GenericResponse<>("User not found", "The user with the given username was not found", "404", null));

    }

    private Map<String, Object> generateExtraClaims(User user) {
        return new HashMap<>() {{
            put("role", user.getRole().name());
            put("name", user.getUsername());
        }};
    }

    @Override
    public GenericResponse<AuthenticationResponse> register(AuthenticationRequest authenticationRequest) {
        var user = new User();
        user.setUsername(authenticationRequest.uasername());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return new GenericResponse<>(null, "User registered successfully", "201", AuthenticationResponse.builder()
                                                                                                            .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                                                                                            .username(user.getUsername())
                                                                                                            .role(user.getRole().name())
                                                                                                            .build());
    }

    @Override
    public GenericResponse<AuthenticationResponse> refresh(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.uasername());
        return user.map(value -> new GenericResponse<>(null, "Token refreshed", "200", AuthenticationResponse.builder()
                                                                                                              .token(jwtService.generateToken(value, generateExtraClaims(value)))
                                                                                                              .username(value.getUsername())
                                                                                                              .role(value.getRole().name())
                                                                                                              .build())).orElseGet(() -> new GenericResponse<>("User not found", "The user with the given username was not found", "404", null));
    }

    @Override
    public GenericResponse<AuthenticationResponse> registerAdmin(AuthenticationRequest authenticationRequest) {
        var user = new User();
        user.setUsername(authenticationRequest.uasername());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return new GenericResponse<>(null, "Admin registered successfully", "201", AuthenticationResponse.builder()
                                                                                                             .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                                                                                             .username(user.getUsername())
                                                                                                             .role(user.getRole().name())
                                                                                                             .build());
    }
}
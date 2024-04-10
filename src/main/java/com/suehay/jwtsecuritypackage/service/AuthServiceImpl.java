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

    /**
     * This method is used to log in a user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The method creates a UsernamePasswordAuthenticationToken and authenticates it using the AuthenticationManager.
     * It then finds the user in the UserRepository using the username from the AuthenticationRequest.
     * If the user is found, it generates a token for the user, creates an AuthenticationResponse, and wraps it in a GenericResponse.
     * If the user is not found, it returns a GenericResponse with an error message.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse if the user is found, or an error message if not.
     */
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

    /**
     * This method is used to generate extra claims for a user.
     * It takes a User object as input and returns a Map with the user's role and username as claims.
     *
     * @param user the User object for which to generate extra claims.
     * @return a Map containing the user's role and username as claims.
     */
    private Map<String, Object> generateExtraClaims(User user) {
        return new HashMap<>() {{
            put("role", user.getRole().name());
            put("name", user.getUsername());
        }};
    }

    /**
     * This method is used to register a new user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The method creates a new User object, sets its username and password from the AuthenticationRequest, and sets its role to USER.
     * It then saves the User in the UserRepository, generates a token for the user, creates an AuthenticationResponse, and wraps it in a GenericResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
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

    /**
     * This method is used to refresh a user's authentication.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The method finds the user in the UserRepository using the username from the AuthenticationRequest.
     * If the user is found, it generates a new token for the user, creates an AuthenticationResponse, and wraps it in a GenericResponse.
     * If the user is not found, it returns a GenericResponse with an error message.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse if the user is found, or an error message if not.
     */
    @Override
    public GenericResponse<AuthenticationResponse> refresh(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.uasername());
        return user.map(value -> new GenericResponse<>(null, "Token refreshed", "200", AuthenticationResponse.builder()
                                                                                                             .token(jwtService.generateToken(value, generateExtraClaims(value)))
                                                                                                             .username(value.getUsername())
                                                                                                             .role(value.getRole().name())
                                                                                                             .build())).orElseGet(() -> new GenericResponse<>("User not found", "The user with the given username was not found", "404", null));
    }

    /**
     * This method is used to register a new admin user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The method creates a new User object, sets its username and password from the AuthenticationRequest, and sets its role to ADMIN.
     * It then saves the User in the UserRepository, generates a token for the user, creates an AuthenticationResponse, and wraps it in a GenericResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
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
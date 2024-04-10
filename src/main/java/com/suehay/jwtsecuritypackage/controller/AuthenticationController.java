package com.suehay.jwtsecuritypackage.controller;

import com.suehay.jwtsecuritypackage.model.request.AuthenticationRequest;
import com.suehay.jwtsecuritypackage.model.response.AuthenticationResponse;
import com.suehay.jwtsecuritypackage.model.response.GenericResponse;
import com.suehay.jwtsecuritypackage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authService;


    /**
     * This method is used to register a new user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The register method of the AuthService is called with the AuthenticationRequest as an argument.
     * The result is wrapped in a ResponseEntity with a CREATED status and returned.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a ResponseEntity containing a GenericResponse with an AuthenticationResponse.
     */
    @PostMapping("/register")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authenticationRequest));
    }

    /**
     * This method is used to log in a user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The login method of the AuthService is called with the AuthenticationRequest as an argument.
     * The result is wrapped in a ResponseEntity with an OK status and returned.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a ResponseEntity containing a GenericResponse with an AuthenticationResponse.
     */
    @PostMapping("/login")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }

    /**
     * This method is used to refresh a user's authentication.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The refresh method of the AuthService is called with the AuthenticationRequest as an argument.
     * The result is wrapped in a ResponseEntity with an OK status and returned.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a ResponseEntity containing a GenericResponse with an AuthenticationResponse.
     */
    @GetMapping("/refresh")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> refresh(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.refresh(authenticationRequest));
    }

    /**
     * This method is used to register a new admin user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The registerAdmin method of the AuthService is called with the AuthenticationRequest as an argument.
     * The result is wrapped in a ResponseEntity with a CREATED status and returned.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a ResponseEntity containing a GenericResponse with an AuthenticationResponse.
     */
    @PostMapping("/register/admin")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> registerAdmin(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerAdmin(authenticationRequest));
    }
}
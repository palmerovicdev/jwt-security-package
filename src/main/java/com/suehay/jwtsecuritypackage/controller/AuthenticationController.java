package com.suehay.jwtsecuritypackage.controller;

import com.suehay.jwtsecuritypackage.model.request.AuthenticationRequest;
import com.suehay.jwtsecuritypackage.model.response.AuthenticationResponse;
import com.suehay.jwtsecuritypackage.model.response.GenericResponse;
import com.suehay.jwtsecuritypackage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authenticationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }
}
package com.suehay.jwtsecuritypackage.service;


import com.suehay.jwtsecuritypackage.model.request.AuthenticationRequest;
import com.suehay.jwtsecuritypackage.model.response.AuthenticationResponse;
import com.suehay.jwtsecuritypackage.model.response.GenericResponse;

public interface AuthService {
    GenericResponse<AuthenticationResponse> login(AuthenticationRequest authenticationRequest);

    GenericResponse<AuthenticationResponse> register(AuthenticationRequest authenticationRequest);

}
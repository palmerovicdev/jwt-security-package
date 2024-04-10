package com.suehay.jwtsecuritypackage.service;


import com.suehay.jwtsecuritypackage.model.request.AuthenticationRequest;
import com.suehay.jwtsecuritypackage.model.response.AuthenticationResponse;
import com.suehay.jwtsecuritypackage.model.response.GenericResponse;

public interface AuthService {
    /**
     * This method is used to log in a user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The result is a GenericResponse containing an AuthenticationResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
    GenericResponse<AuthenticationResponse> login(AuthenticationRequest authenticationRequest);

    /**
     * This method is used to register a new user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The result is a GenericResponse containing an AuthenticationResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
    GenericResponse<AuthenticationResponse> register(AuthenticationRequest authenticationRequest);

    /**
     * This method is used to refresh a user's authentication.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The result is a GenericResponse containing an AuthenticationResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
    GenericResponse<AuthenticationResponse> refresh(AuthenticationRequest authenticationRequest);

    /**
     * This method is used to register a new admin user.
     * It takes an AuthenticationRequest object as input, which contains the user's credentials.
     * The result is a GenericResponse containing an AuthenticationResponse.
     *
     * @param authenticationRequest the AuthenticationRequest containing the user's credentials.
     * @return a GenericResponse containing an AuthenticationResponse.
     */
    GenericResponse<AuthenticationResponse> registerAdmin(AuthenticationRequest authenticationRequest);
}
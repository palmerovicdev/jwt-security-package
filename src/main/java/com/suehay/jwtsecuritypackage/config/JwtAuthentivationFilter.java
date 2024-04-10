package com.suehay.jwtsecuritypackage.config;

import com.suehay.jwtsecuritypackage.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthentivationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * This method is used to filter each request and perform JWT authentication.
     * It is overridden from OncePerRequestFilter class.
     *
     * @param request     the HttpServletRequest which contains the request the client has made of the server.
     * @param response    the HttpServletResponse, which contains the response the server sends to the client.
     * @param filterChain the FilterChain is provided to invoke the next entity in the chain.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the Authorization header from the request.
        var header = request.getHeader("Authorization");

        // If the header is null or does not contain "Bearer ", continue with the filter chain and return.
        if (header == null || !header.contains("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header.
        var jwtToken = header.split(" ")[1];

        // Retrieve the username from the JWT token.
        var username = jwtService.getUsernameFromToken(jwtToken);

        // Retrieve the user from the UserRepository using the username.
        // If the user is not found, throw a RuntimeException.
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Unable to authenticate with username: " + username));

        // Create a UsernamePasswordAuthenticationToken using the user's details.
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        // Set the authentication in the SecurityContext.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // Continue with the filter chain.
        filterChain.doFilter(request, response);
    }
}
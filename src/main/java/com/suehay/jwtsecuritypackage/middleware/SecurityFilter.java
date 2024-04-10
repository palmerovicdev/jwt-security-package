package com.suehay.jwtsecuritypackage.middleware;

import com.suehay.jwtsecuritypackage.config.JwtAuthentivationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthentivationFilter jwtAuthentivationFilter;

    // INFO: this must change between projects

    /**
     * This method is used to configure the security filter chain for the application.
     * It is annotated with @Bean to indicate that it is a factory for creating a Spring bean.
     * The returned SecurityFilterChain bean is automatically registered in the Spring application context.
     *
     * @param http an HttpSecurity instance used to build the security filter chain.
     * @return the built SecurityFilterChain.
     * @throws Exception if any error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Configure the session management to be stateless.
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the authentication provider.
                .authenticationProvider(authenticationProvider)
                // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(jwtAuthentivationFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure the authorization for HTTP requests.
                .authorizeHttpRequests(authConfig -> {
                    // Configure the authorization for specific routes.
                    authConfig
                            // Allow anyone to register.
                            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                            // Allow anyone to log in.
                            .requestMatchers(HttpMethod.GET, "/auth/login").permitAll()
                            // Allow anyone to refresh their authentication.
                            .requestMatchers(HttpMethod.GET, "/auth/refresh").permitAll()
                            // Only allow users with the "ROOT" authority to register as an admin.
                            .requestMatchers(HttpMethod.POST, "/auth/register/admin").hasAnyAuthority("ROOT")
                            // Allow anyone to access the error page.
                            .requestMatchers("/error").permitAll();

                    // Deny all other requests.
                    authConfig.anyRequest().denyAll();
                });

        // Build and return the security filter chain.
        return http.build();
    }
}
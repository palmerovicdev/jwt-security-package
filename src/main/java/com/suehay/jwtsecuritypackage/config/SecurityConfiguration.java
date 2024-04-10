package com.suehay.jwtsecuritypackage.config;

import com.suehay.jwtsecuritypackage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserRepository userRepository;

    /**
     * This method is used to create an AuthenticationManager bean that will be managed by Spring.
     * The AuthenticationManager is retrieved from the provided AuthenticationConfiguration.
     *
     * @param configuration the AuthenticationConfiguration from which the AuthenticationManager is retrieved.
     * @return an AuthenticationManager that is managed by Spring.
     * @throws Exception if there is an error retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * This method is used to create an AuthenticationProvider bean that will be managed by Spring.
     * It creates a DaoAuthenticationProvider and sets its PasswordEncoder and UserDetailsService.
     * The PasswordEncoder is set using the passwordEncoder method.
     * The UserDetailsService is set using the userDetailsService method.
     *
     * @return an AuthenticationProvider that is managed by Spring and configured with a PasswordEncoder and UserDetailsService.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    /**
     * This method is used to create a PasswordEncoder bean that will be managed by Spring.
     * It uses the BCrypt hashing function for encoding passwords.
     *
     * @return a PasswordEncoder that uses BCrypt for password hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is used to create a UserDetailsService bean that will be managed by Spring.
     * It uses a lambda function to define the loadUserByUsername method of the UserDetailsService.
     * The loadUserByUsername method uses the UserRepository to find a user by their username.
     * If a user with the given username is not found, a RuntimeException is thrown.
     *
     * @return a UserDetailsService that uses the UserRepository to find users by their username.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                                         .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
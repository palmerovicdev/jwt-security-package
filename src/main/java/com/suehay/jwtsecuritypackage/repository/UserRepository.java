package com.suehay.jwtsecuritypackage.repository;

import com.suehay.jwtsecuritypackage.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This method is used to find a User entity by its username.
     * It takes a String username as a parameter and returns an Optional<User>.
     * The Optional<User> will be empty if no User with the given username is found.
     *
     * @param username the username of the User to find.
     * @return an Optional<User> containing the User if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
package com.dheker.securityDvApp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


//fetch user by email
    //The method returns a User object wrapped in an Optional,
// meaning the result may or may not be present (i.e., null-safe).



    //🧠 Why use Optional?
    //To avoid NullPointerException
    Optional<User> findByUsername(String email);












}

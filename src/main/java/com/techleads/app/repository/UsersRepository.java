package com.techleads.app.repository;

import com.techleads.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUserByEmail(String email);
}



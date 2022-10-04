package com.sadegh.blogrestapi.repository;

import com.sadegh.blogrestapi.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String email);
    Optional<User>findByUsernameOrEmail(String username,String email);
    Optional<User>findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

package com.nursy.nursy.repository;

import com.nursy.nursy.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserIdentifier(String identifier);
}

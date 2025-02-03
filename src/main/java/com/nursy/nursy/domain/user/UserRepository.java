package com.nursy.nursy.domain.user;

import com.nursy.nursy.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserIdentifier(String identifier);
}

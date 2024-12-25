package com.nursy.nursy.repository;

import com.nursy.nursy.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}

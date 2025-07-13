package com.oscar.usermicroservice.repository;

import com.oscar.usermicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

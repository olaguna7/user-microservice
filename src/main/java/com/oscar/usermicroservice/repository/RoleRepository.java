package com.oscar.usermicroservice.repository;

import com.oscar.usermicroservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

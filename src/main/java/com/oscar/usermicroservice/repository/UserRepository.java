package com.oscar.usermicroservice.repository;

import com.oscar.usermicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRolesRoleId(Long roleId);

}

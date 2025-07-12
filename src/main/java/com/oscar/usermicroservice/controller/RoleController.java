package com.oscar.usermicroservice.controller;

import com.oscar.usermicroservice.dto.RoleDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.service.RoleService;
import com.oscar.usermicroservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO role = roleService.createRole(roleDTO);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{roleId}")
    public RoleDTO getRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.findById(roleId);
    }

    @GetMapping("/{roleId}/users")
    public List<UserDTO> getUsersByRole(@PathVariable("roleId") Long roleId) {
        return userService.findUsersByRoleId(roleId);
    }

}

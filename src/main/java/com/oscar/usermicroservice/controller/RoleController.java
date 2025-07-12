package com.oscar.usermicroservice.controller;

import com.oscar.usermicroservice.dto.RoleDTO;
import com.oscar.usermicroservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
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

}

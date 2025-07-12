package com.oscar.usermicroservice.mapper;

import com.oscar.usermicroservice.dto.RoleDTO;
import com.oscar.usermicroservice.entity.Role;

import java.util.List;

public class RoleMapper {

    private RoleMapper() {}

    public static RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getName());
    }

    public static List<RoleDTO> toDTOList(List<Role> roles) {
        return roles.stream().map(RoleMapper::toDTO).toList();
    }

    public static Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;
    }

    public static List<Role> toEntityList(List<RoleDTO> rolesDTO) {
        return rolesDTO.stream().map(RoleMapper::toEntity).toList();
    }
}

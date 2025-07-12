package com.oscar.usermicroservice.service;

import com.oscar.usermicroservice.dto.RoleDTO;
import com.oscar.usermicroservice.mapper.RoleMapper;
import com.oscar.usermicroservice.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        return RoleMapper.toDTO(roleRepository.save(RoleMapper.toEntity(roleDTO)));
    }

    public List<RoleDTO> findAll() {
        return RoleMapper.toDTOList(roleRepository.findAll());
    }

    public RoleDTO findById(Long roleId) {
        return RoleMapper.toDTO(roleRepository.findById(roleId).orElseThrow(EntityExistsException::new));
    }

}

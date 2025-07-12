package com.oscar.usermicroservice.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private List<RoleDTO> roles;
}

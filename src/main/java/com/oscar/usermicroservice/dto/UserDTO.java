package com.oscar.usermicroservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
}

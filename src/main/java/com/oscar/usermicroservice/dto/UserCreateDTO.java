package com.oscar.usermicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}

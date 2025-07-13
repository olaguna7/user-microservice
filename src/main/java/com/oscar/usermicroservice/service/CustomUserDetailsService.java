package com.oscar.usermicroservice.service;

import com.oscar.usermicroservice.entity.User;
import com.oscar.usermicroservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        log.info("=========================================");
        log.info("Intentando autenticar usuario: '{}'", usernameOrEmail);

        // Logs de depuración
        List<User> allUsers = userRepository.findAll();
        log.info("Usuarios en la base de datos: {}", allUsers.size());
        for (User u : allUsers) {
            log.info("Usuario en BD: '{}', Email: '{}'", u.getUsername(), u.getEmail());
        }

        // Búsqueda corregida
        Optional<User> byUsername = userRepository.findUserByUsername(usernameOrEmail);
        Optional<User> byEmail = userRepository.findUserByEmail(usernameOrEmail);

        log.info("Búsqueda por username '{}': {}", usernameOrEmail, byUsername.isPresent());
        log.info("Búsqueda por email '{}': {}", usernameOrEmail, byEmail.isPresent());

        // Lógica corregida
        User user;
        if (byUsername.isPresent()) {
            user = byUsername.get();
            log.info("Usuario encontrado por username: '{}'", user.getUsername());
        } else if (byEmail.isPresent()) {
            user = byEmail.get();
            log.info("Usuario encontrado por email: '{}'", user.getUsername());
        } else {
            log.error("Usuario no encontrado: '{}'", usernameOrEmail);
            throw new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
        }

        log.info("Password hash: '{}'", user.getPassword().substring(0, 10) + "...");
        log.info("=========================================");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}

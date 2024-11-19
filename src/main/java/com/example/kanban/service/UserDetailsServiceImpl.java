package com.example.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Você pode implementar a lógica para buscar usuários de um banco de dados.
    // Aqui, estamos criando um usuário em memória para simplificar.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usuário padrão para exemplo
        if ("usuario_exemplo".equals(username)) {
            return User.builder()
                    .username("usuario_exemplo")
                    .password(passwordEncoder.encode("senha_exemplo"))
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}

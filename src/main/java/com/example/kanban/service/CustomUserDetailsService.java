package com.example.kanban.service;

import com.example.kanban.model.KanbanUser; // Importa a classe User que representa o usuário
import com.example.kanban.repository.UserRepository; // Importa o repositório para acessar os dados do usuário

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Indica que esta classe é um componente de serviço do Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Injeção de dependência do repositório

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no repositório pelo nome de usuário
        KanbanUser user = userRepository.findByUsername(username);
        
        // Se o usuário não for encontrado, lança uma exceção
        if (user == null) {
            throw new UsernameNotFoundException("User  not found with username: " + username);
        }
        
        if (user.getAuthorities() == null) {
            user.setAuthorities(new ArrayList<>());
        }


        // Retorna uma instância de CustomUser Details com as informações do usuário encontrado
        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
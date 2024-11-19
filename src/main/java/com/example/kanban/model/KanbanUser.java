package com.example.kanban.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class KanbanUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection
    private List<String> authorities = new ArrayList<>();; // Armazena as permissões como uma lista de strings

    // Construtor padrão
    public KanbanUser() {
    }

    public KanbanUser(String username, String password, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities != null ? authorities : new ArrayList<>();
    }

    // Implementação dos métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)  // Converte a lista de strings em autoridades reais
            .toList();
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities != null ? authorities : new ArrayList<>();
    }
}

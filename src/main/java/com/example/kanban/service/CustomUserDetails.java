package com.example.kanban.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        // Garantir que authorities não seja null
        this.authorities = authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true; // Pode ser alterado conforme a lógica do seu aplicativo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode ser alterado conforme a lógica do seu aplicativo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode ser alterado conforme a lógica do seu aplicativo
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode ser alterado conforme a lógica do seu aplicativo
    }
}

package com.foodway.api.service.user.authentication.dto;

import com.foodway.api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Collection;
import java.util.UUID;

public class UserDetailsDto implements UserDetails {

    private final UUID id;
    private final String email;
    private final String password;
    private final String name;
    private final String typeUser;

    public UserDetailsDto(User user) {
        this.id = user.getIdUser();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.name = user.getName();
        this.typeUser = user.getTypeUser().toString();
        // Outros mapeamentos de campos, se necessário
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public String getEmail() {
        return email;
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
}

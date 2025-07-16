package com.dheker.securityDvApp.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder //making object creation cleaner and more readable
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue//auto geenrated
    private Integer id;
    private String name;
    private String    lastname;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
private Role  role;


    //Cette méthode retourne la liste des rôles ou autorisations (authorities)
    // qu’a l’utilisateur dans une application sécurisée avec Spring Security.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }


    //return username

    @Override
    public String getUsername()

    {
        return email;
    }
//tu ne gères pas de date d’expiration de compte, r
    @Override
    public boolean isAccountNonExpired() {

        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

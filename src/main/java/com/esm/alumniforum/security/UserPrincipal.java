package com.esm.alumniforum.security;

import com.esm.alumniforum.config.UserIdentity;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.user.model.Profile;
import com.esm.alumniforum.user.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
//@Component("customUserPrincipal")
public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private Organisation organisation;

    @JsonIgnore
    @Autowired
    private static UserIdentity userIdentity;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String email, String password,
                         Collection<? extends GrantedAuthority> authorities, Organisation organisation) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.organisation = organisation;

        if (authorities == null) {
            this.authorities = null;
        } else {
            this.authorities = new ArrayList<>(authorities);
        }
    }

    public static UserPrincipal create(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        userIdentity = UserIdentity.builder().id(Optional.ofNullable(user.getId()).orElse(null))
                        .roles(Optional.ofNullable(user.getRoles()).orElse(null))
                                .organisation(Optional.ofNullable(user.getOrganisation()).orElse(null))
                                        .email(Optional.ofNullable(user.getEmail()).orElse(null))
                                                .profile(Optional.ofNullable(user.getProfile()).orElse(null))
                                                        .build();
//        userIdentity.setRoles(Optional.ofNullable(user.getRoles()).orElse(null));
//        userIdentity.setEmail(Optional.ofNullable(user.getEmail()).orElse(null));
//        userIdentity.setId(Optional.ofNullable(user.getId()).orElse(null));
//        userIdentity.setOrganisation(Optional.ofNullable(user.getOrganisation()).orElse(null));
//        userIdentity.setProfile(Optional.ofNullable(user.getProfile()).orElse(null));


        return new UserPrincipal(user.getId(),
                user.getEmail(), user.getPassword(), authorities, user.getOrganisation());
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
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

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        UserPrincipal that = (UserPrincipal) object;
        return Objects.equals(id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }
}


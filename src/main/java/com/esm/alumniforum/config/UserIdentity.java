package com.esm.alumniforum.config;

import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.user.model.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Set;

//@Component
@Setter
@Getter
@Builder
@Component
@SessionScope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserIdentity {
    private String id;
    private String email;
    private Organisation organisation;
    private Profile profile;
    private Set<String> roles;

//    public UserIdentity(String id, String email, Organisation organisation, Profile profile, Set<String> roles) {
//        this.id = id;
//        this.email = email;
//        this.organisation = organisation;
//        this.profile = profile;
//        this.roles = roles;
//    }

//    public UserIdentity createIdentity(Users users){
//
//
//        return UserIdentity.builder()
//                .email(Optional.ofNullable(users.getEmail()).orElse(null))
//                .organisation(Optional.ofNullable(users.getOrganisation()).orElse(null))
//                .id(users.getId())
//                .profile(Optional.ofNullable(users.getProfile()).orElse(null))
//                .roles(Optional.ofNullable(users.getRoles()).orElse(null))
//                .build();
//    }
//public UserIdentity() {
//
//}
}

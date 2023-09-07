package com.esm.alumniforum.user.dto;

import lombok.*;

import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    private String email;
    private String id;
    private Set<String> roles;
    private ProfileResponse profileResponse;
    private String orgId;
    private String orgName;
}

package com.esm.alumniforum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileForm {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String profilePictureUrl;
}

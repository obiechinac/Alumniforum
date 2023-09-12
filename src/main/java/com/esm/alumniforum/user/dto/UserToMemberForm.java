package com.esm.alumniforum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserToMemberForm {

    private String userId;
    private String dateJoined;
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String profilePictureUrl;
}

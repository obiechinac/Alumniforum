package com.esm.alumniforum.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberForm {
    private String id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String profilePictureUrl;
    private String organisationId;
    private String dateJoined;


}

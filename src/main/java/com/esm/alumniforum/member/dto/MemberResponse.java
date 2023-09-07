package com.esm.alumniforum.member.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String profilePictureUrl;
}

package com.esm.alumniforum.user.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
//    private String profilePicture;
}

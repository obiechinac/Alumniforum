package com.esm.alumniforum.organisation.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrganisationResponse {
    private String  orgName ;
    private String  description;
    private String  motto;
    private String  purpose;
    private String  address;
    private String  email;
    private String  phone;
    private String  createdDate;
}

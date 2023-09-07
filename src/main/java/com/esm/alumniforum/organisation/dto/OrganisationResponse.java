package com.esm.alumniforum.organisation.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationResponse {
    private String  orgName ;
    private String  description;
    private String  motto;
    private String  purpose;
    private String  address;
    private String  email;
    private String  phone;
    private LocalDate createdDate;
    private String id;
}

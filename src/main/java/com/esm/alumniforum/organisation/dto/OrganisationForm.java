package com.esm.alumniforum.organisation.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationForm {
    private String id;
    private String orgName ;
    private String  description;
    private String  motto;
    private String  purpose;
    private String  address;
    private String  email;
    private String  phone;
}

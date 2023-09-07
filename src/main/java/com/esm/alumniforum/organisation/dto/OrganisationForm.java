package com.esm.alumniforum.organisation.dto;

import com.esm.alumniforum.enums.OrganisationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrganisationForm {
    private String id;
    @NotBlank(message = "You must provide an organization name")
    private String orgName;
    private String  description;
    private String  motto;
    private String  purpose;
    @Email(message = "Invalid email format")
    private String  address;
    private String  email;
    private String  phone;
    private String  country;
    private LocalDate establishedDate;

    @NotNull(message = "Type must be  SCHOOL,INTER_SOCIETY,CLUB,NGO, COOPERATIVE, FAMILY, FRIENDS")
    @Enumerated(EnumType.STRING)
    private OrganisationType organisationType;
}

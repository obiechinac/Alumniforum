package com.esm.alumniforum.constitution.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ConstitutionForm {
    private String organisationId;
    private String id;
    private String description;
    private MultipartFile file;
}

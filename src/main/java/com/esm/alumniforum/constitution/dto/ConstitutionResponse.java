package com.esm.alumniforum.constitution.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConstitutionResponse {
    private String id;
    private String  orgName ;
    private String  orgId;
    private String  description;
    private byte[] file;
    private LocalDate createdDate;
}

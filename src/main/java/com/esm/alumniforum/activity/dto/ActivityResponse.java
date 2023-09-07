package com.esm.alumniforum.activity.dto;

import com.esm.alumniforum.organisation.model.Organisation;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private String  name;
    private String  description;
    private Organisation organisation;
    private String activityType;
    private String paymentType;
    private String paymentNature;
    private LocalDate createdDate;
}

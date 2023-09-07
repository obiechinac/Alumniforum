package com.esm.alumniforum.activity.dto;

import com.esm.alumniforum.enums.ActivityType;
import com.esm.alumniforum.enums.PaymentNature;
import com.esm.alumniforum.enums.PaymentType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ActivityForm {
    private String id;
    private String name;
    private String description;
    private String organisationId;
    private LocalDate date;
    private ActivityType activityType;
    private PaymentType paymentType;
    @NotEmpty(message = "paymentNature cannot be empty")
    private PaymentNature paymentNature;
}

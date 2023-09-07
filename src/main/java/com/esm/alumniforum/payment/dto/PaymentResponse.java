package com.esm.alumniforum.payment.dto;

import com.esm.alumniforum.organisation.model.Organisation;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
//    private String  name;
    private String  description;
    private Organisation organisation;
    private String activityType;
    private String paymentType;
    private String paymentNature;
    private LocalDate createdDate;
    private String activityId;
    private String requiredAmount;

}

package com.esm.alumniforum.individualpay.dto;

import com.esm.alumniforum.organisation.model.Organisation;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualPayResponse {
    private String  id;
    private Organisation organisation;
    private String paymentType;
    private String paymentNature;
    private LocalDate createdDate;
    private String paymentId;
    private String requiredAmount;
    private String paidAmount;
    private String paidBy;
//    private

}

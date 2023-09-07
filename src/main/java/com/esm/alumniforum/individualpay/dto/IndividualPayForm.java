package com.esm.alumniforum.individualpay.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualPayForm {
    private String id;
    private String organisationId;
    @NotEmpty(message = "paymentId cannot be empty")
    private String paymentId;
    @NotEmpty(message = "memberId must not be empty")
    private String memberId;
    @NotEmpty(message = "paidAmount must not be empty")
    private String paidAmount;
}

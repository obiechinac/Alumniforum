package com.esm.alumniforum.payment.dto;

import com.esm.alumniforum.activity.model.Activity;
import com.esm.alumniforum.enums.ActivityType;
import com.esm.alumniforum.enums.PaymentNature;
import com.esm.alumniforum.enums.PaymentType;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.user.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentForm {
    private String id;
    private String organisationId;
    private ActivityType activityType;
    private PaymentType paymentType;
    private PaymentNature paymentNature;
    private String activityId;
    private String usersId;
    private String requiredAmount;

}

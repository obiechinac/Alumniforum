package com.esm.alumniforum.payment.model;

import com.esm.alumniforum.activity.model.Activity;
import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.enums.PaymentNature;
import com.esm.alumniforum.enums.PaymentType;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.user.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class Payment extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "payment_nature")
    @Enumerated(EnumType.STRING)
    private PaymentNature paymentNature;
    @Column(name = "is_deleted")
    private Boolean  isDeleted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    @Column(name = "required_amount")
    private String requiredAmount;
//    @Column(name = "paid_amount")
//    private String paidAmount;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private Activity activity;

//    @ManyToOne
//    @JoinColumn(name = "users_id")
//    private Users users;

    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "PAYM-" + uuidString;
    }

}

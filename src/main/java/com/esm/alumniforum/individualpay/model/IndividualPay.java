package com.esm.alumniforum.individualpay.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.member.model.Member;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.payment.model.Payment;
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
@Table(name = "individual_pay")
public class IndividualPay extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "is_deleted")
    private Boolean  isDeleted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    @Column(name = "required_amount")
    private String requiredAmount;
    @Column(name = "paid_amount")
    private String paidAmount;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "INDPAY-" + uuidString;
    }

}

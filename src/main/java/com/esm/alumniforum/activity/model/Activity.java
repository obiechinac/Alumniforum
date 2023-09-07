package com.esm.alumniforum.activity.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.enums.ActivityType;
import com.esm.alumniforum.enums.PaymentNature;
import com.esm.alumniforum.enums.PaymentType;
import com.esm.alumniforum.organisation.model.Organisation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    private String name;
    private String description;
    private LocalDate date;
    @Column(name = "is_deleted")
    private Boolean  isDeleted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "payment_nature")
    @Enumerated(EnumType.STRING)
    private PaymentNature paymentNature;
    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "ACTI-" + uuidString;
    }
}

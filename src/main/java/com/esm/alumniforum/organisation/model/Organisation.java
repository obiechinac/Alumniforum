package com.esm.alumniforum.organisation.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.enums.OrganisationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "organisation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "org_name")
    private String orgName ;
    @Column(name = "description")
    private String  description;
    @Column(name = "motto")
    private String  motto;
    @Column(name = "purpose")
    private String  purpose;
    @Column(name = "address")
    private String  address;
    @Column(name = "email")
    private String  email;
    @Column(name = "phone")
    private String  phone;
    @Column(name = "is_deleted")
    private Boolean  isDeleted;
    @Column(name = "is_active")
    private Boolean  isActive;
    @Column(name = "org_type")
    @Enumerated(EnumType.STRING)
    private OrganisationType organisationType;
    private String  country;
    @Column(name = "established_date")
    private LocalDate establishedDate;
    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "ORG-" + uuidString;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "id='" + id + '\'' +
                ", orgName='" + orgName + '\'' +
                ", description='" + description + '\'' +
                ", motto='" + motto + '\'' +
                ", purpose='" + purpose + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

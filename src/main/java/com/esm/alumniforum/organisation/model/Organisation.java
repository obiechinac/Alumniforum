package com.esm.alumniforum.organisation.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(schema = "organisation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation extends PersistCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_sequence")
    @SequenceGenerator(name = "org_sequence", sequenceName = "org_sequence")
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
    @PrePersist
    private void generateId() {
        this.id = "ORG_" + this.id;
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

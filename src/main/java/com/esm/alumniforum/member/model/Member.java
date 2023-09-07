package com.esm.alumniforum.member.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.organisation.model.Organisation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
@Builder
public class Member extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "other_name")
    private String otherName;
    @Column(name = "address")
    private String address;
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    @Column(name = "date_joined")
    private String dateJoined;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "MEMB-" + uuidString;
    }
}

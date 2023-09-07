package com.esm.alumniforum.user.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.enums.OrganisationMemberType;
import com.esm.alumniforum.organisation.model.Organisation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

@Column(name = "email", nullable = false, unique = true)
@NotEmpty(message = "Email must not be empty")
@Email(message = "Email must be a valid email address")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_suspended")
    private boolean isSuspended = false;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> authorities = new HashSet<>();
    public void addRole(String role) {
        roles.add(role);
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Column(name="member_type")
    @Enumerated(EnumType.STRING)
    private OrganisationMemberType memberType;

    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "USR-" + uuidString;
    }
}

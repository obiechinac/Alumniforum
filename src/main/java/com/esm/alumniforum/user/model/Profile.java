package com.esm.alumniforum.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users_profile")
@NoArgsConstructor
@Builder
public class Profile {
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
    @Column(name = "user_id")
    private String userId;




    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "USRPR-" + uuidString;
    }
}

package com.esm.alumniforum.rolesAndPermission;

import com.esm.alumniforum.common.entity.PersistCommon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
@Column(name = "name")
private String name;

    @PrePersist
    private void generateId() {
        String uuidString = UUID.randomUUID().toString();
        id = "ROL-" + uuidString;
    }
}

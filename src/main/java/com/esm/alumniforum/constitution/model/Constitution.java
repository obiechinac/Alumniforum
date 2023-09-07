package com.esm.alumniforum.constitution.model;

import com.esm.alumniforum.common.entity.PersistCommon;
import com.esm.alumniforum.organisation.model.Organisation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "constitution")
@Getter
@Setter
public class Constitution extends PersistCommon {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "description")
    private String description;

    @Column(name = "file_document")
    private byte[] file;
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @PrePersist
private void generateId(){
    String uiidString = UUID.randomUUID().toString();

    id= "CON-"+uiidString;
}
}

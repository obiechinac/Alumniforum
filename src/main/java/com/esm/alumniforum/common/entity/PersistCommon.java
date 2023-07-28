package com.esm.alumniforum.common.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@Getter
@Setter
@ToString
public class PersistCommon {

 @Column(name="created_by")
 private String createdBy;
 @Column(name="modified_by")
 private String modifiedBy;
 @Column(name="create_date")
 private LocalDate createdDate;
 @Column(name="modified_date")
 private LocalDate   modifiedDate;

}

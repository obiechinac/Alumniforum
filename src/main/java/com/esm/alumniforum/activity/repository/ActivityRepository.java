package com.esm.alumniforum.activity.repository;

import com.esm.alumniforum.activity.model.Activity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Query("select a from Activity a where a.isDeleted= false and a.id =:id")
    Optional<Activity> findById(@NotBlank String id);

    @Query("select a from Activity a where a.isDeleted= false")
    Page<Activity> findAll(PageRequest pageRequest);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=NONE and a.organisation.id=:orgId")
    Page<Activity> findAllUnpaidByOrg(PageRequest pageRequest,String orgId);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=NONE and a.organisation.id=:orgId")
    Page<Activity> findAllUnpaidByMyOrg(PageRequest pageRequest,String orgId);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=VOLUNTARY and a.organisation.id=:orgId")
    Page<Activity> findAllVoluntaryByOrg(PageRequest pageRequest, String orgId);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=VOLUNTARY and a.organisation.id=:orgId")
    Page<Activity> findAllVoluntaryByMyOrg(PageRequest pageRequest, String orgId);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=COMPULSORY and a.organisation.id=:orgId")
    Page<Activity> findAllCompulsoryByOrg(PageRequest pageRequest,String orgId);
    @Query("select a from Activity a where a.isDeleted= false and a.paymentNature=COMPULSORY and a.organisation.id=:orgId")
    Page<Activity> findAllCompulsoryByMyOrg(PageRequest pageRequest,String orgId);
}

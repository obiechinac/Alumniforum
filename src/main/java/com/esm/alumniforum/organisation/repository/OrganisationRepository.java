package com.esm.alumniforum.organisation.repository;

import com.esm.alumniforum.organisation.model.Organisation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {
    @Query("select o from Organisation o where o.isDeleted= false and o.id =:id")
    Optional<Organisation> findById(@NotBlank String id);

    @Query("select o from Organisation o where o.isDeleted= false")
    Page<Organisation> findAll(PageRequest pageRequest);
}

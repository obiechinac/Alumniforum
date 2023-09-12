package com.esm.alumniforum.constitution.repository;

import com.esm.alumniforum.constitution.model.Constitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConstitutionRepository extends JpaRepository<Constitution, String> {

    @Query("FROM Constitution WHERE id =:id and isDeleted=false and organisation.id=:orgId")
    Optional<Constitution> findById( @Param("id") String id, @Param("orgId") String orgId);

    @Query("select c from Constitution c where c.isDeleted= false")
    Page<Constitution> findAll(PageRequest pageRequest);

    @Query("FROM Constitution where organisation.id =:orgId and isDeleted=false ")
    List<Constitution> findAllByOrg(@Param("orgId") String orgId);
    @Query("FROM Constitution where organisation.id =:orgId and isDeleted=false ")
    List<Constitution> findAllByMyOrg(@Param("orgId") String orgId);
}

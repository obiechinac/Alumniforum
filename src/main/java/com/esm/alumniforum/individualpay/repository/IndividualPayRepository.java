package com.esm.alumniforum.individualpay.repository;

import com.esm.alumniforum.individualpay.model.IndividualPay;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualPayRepository extends JpaRepository<IndividualPay, String> {
    @Query("select p from IndividualPay p where p.isDeleted= false and p.id =:id")
    Optional<IndividualPay> findById(@NotBlank String id);

    @Query("select p from IndividualPay p where p.isDeleted= false")
    Page<IndividualPay> findAll(PageRequest pageRequest);

    @Query("FROM IndividualPay  WHERE isDeleted= false and payment.id=:paymentId")
    Page<IndividualPay> findAllByPaymentId(PageRequest pageRequest, String paymentId);
}

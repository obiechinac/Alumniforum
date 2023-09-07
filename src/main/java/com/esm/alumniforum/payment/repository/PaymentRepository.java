package com.esm.alumniforum.payment.repository;

import com.esm.alumniforum.activity.model.Activity;
import com.esm.alumniforum.payment.model.Payment;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("select p from Payment p where p.isDeleted= false and p.id =:id")
    Optional<Payment> findById(@NotBlank String id);

    @Query("select p from Activity p where p.isDeleted= false")
    Page<Payment> findAll(PageRequest pageRequest);
}

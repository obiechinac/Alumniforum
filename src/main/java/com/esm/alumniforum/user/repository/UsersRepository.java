package com.esm.alumniforum.user.repository;

import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.user.model.Users;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    @Query("FROM Users WHERE email=:email and isDeleted=false")
    Optional<Users> findByEmail(@NotBlank @Param("email") String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    @Query("FROM Users WHERE id=:id and isDeleted=false")
    Optional<Users> findById(@NotBlank @Param("id") String id);
    @Query("select o from Users o where o.isDeleted= false")
    Page<Users> findAll(PageRequest pageRequest);
}

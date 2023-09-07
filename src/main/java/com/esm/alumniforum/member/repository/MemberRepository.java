package com.esm.alumniforum.member.repository;

import com.esm.alumniforum.member.model.Member;
import com.esm.alumniforum.user.model.Profile;
import com.esm.alumniforum.user.model.Users;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("FROM Member WHERE id=:id and isDeleted=false")
    Optional<Member> findById(@NotBlank @Param("id") String id);
    @Query("select m from Member m where m.isDeleted= false")
    Page<Member> findAll(PageRequest pageRequest);
    @Query("FROM Member  where isDeleted= false and organisation.id=:orgId")
    Page<Member> findAllByOrg(PageRequest pageRequest, String orgId);
    @Query("FROM Member  where isDeleted= false and organisation.id=:orgId")
    Page<Member> findAllByMyOrg(PageRequest pageRequest, String orgId);
}

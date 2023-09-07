package com.esm.alumniforum.member.service.interfa;


import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.member.dto.MemberForm;
import com.esm.alumniforum.member.dto.MemberResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

public interface MemberService {
    MemberResponse save(MemberForm memberform, UserPrincipal principal);
    MemberResponse update(MemberForm membForm, UserPrincipal principal);
    MemberResponse findById(String id);
    PagedResponse<MemberResponse> findAll(PageRequest pageRequest);
    void delete(String id);

    PagedResponse<MemberResponse> findAllByOrg(PageRequest pageRequest, String orgId);

    PagedResponse<MemberResponse> findAllByMyOrg(PageRequest pageRequest, String orgId);
}

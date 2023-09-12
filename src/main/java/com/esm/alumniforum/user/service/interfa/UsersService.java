package com.esm.alumniforum.user.service.interfa;

import com.esm.alumniforum.common.response.PagedResponse;

import com.esm.alumniforum.member.dto.MemberResponse;
import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.dto.*;
import org.springframework.data.domain.PageRequest;

public interface UsersService {
    UsersResponse save(UsersForm form, UserPrincipal principal);
    ProfileResponse update(ProfileForm form);

    ImageResponse updateProfilePicture(ImageForm form);

    UsersResponse findById(String id);
    UsersResponse findByEmail(String id);
    PagedResponse<UsersResponse> findAll(PageRequest pageRequest);
    void delete(String id);
    boolean existsByEmail(String email);

    MemberResponse userToMember(UserToMemberForm form, String createdBy);

    UsersResponse memberToUser(MemberToUserForm form, UserPrincipal principal);

}

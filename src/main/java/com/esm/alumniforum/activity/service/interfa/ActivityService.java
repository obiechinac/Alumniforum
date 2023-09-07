package com.esm.alumniforum.activity.service.interfa;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

public interface ActivityService {
    ActivityResponse save(ActivityForm activityform, UserPrincipal principal);

    ActivityResponse update(ActivityForm actForm, UserPrincipal principal);
    ActivityResponse findById(String id);
    PagedResponse<ActivityResponse> findAll(PageRequest pageRequest);
    void delete(String id);

    PagedResponse<ActivityResponse> findAllUnpaidByOrg(PageRequest pageRequest, String orgId);

    PagedResponse<ActivityResponse> findAllUnpaidByMyOrg(PageRequest pageRequest, String id);

    PagedResponse<ActivityResponse> findAllVoluntaryByOrg(PageRequest pageRequest, String orgId);

    PagedResponse<ActivityResponse> findAllVoluntaryByMyOrg(PageRequest pageRequest, String id);
    PagedResponse<ActivityResponse> findAllCompulsoryByOrg(PageRequest pageRequest, String orgId);

    PagedResponse<ActivityResponse> findAllCompulsoryByMyOrg(PageRequest pageRequest, String id);
}

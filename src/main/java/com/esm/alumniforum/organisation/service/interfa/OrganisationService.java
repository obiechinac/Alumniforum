package com.esm.alumniforum.organisation.service.interfa;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

public interface OrganisationService {
    OrganisationResponse save(OrganisationForm orgForm, UserPrincipal principal);

    OrganisationResponse update(OrganisationForm orgForm, UserPrincipal principal);
    OrganisationResponse findById(String id);
    PagedResponse<OrganisationResponse> findAll(PageRequest pageRequest);
    void delete(String id);

    OrganisationResponse save(OrganisationForm organisationForm);
}

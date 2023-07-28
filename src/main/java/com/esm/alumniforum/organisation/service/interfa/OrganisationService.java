package com.esm.alumniforum.organisation.service.interfa;

import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface OrganisationService {
    OrganisationResponse save(OrganisationForm orgForm);
    OrganisationResponse update(OrganisationForm orgForm);
    OrganisationResponse findById();
    Page<OrganisationResponse> findAll(PageRequest pageRequest);
}

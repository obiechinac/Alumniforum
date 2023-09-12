package com.esm.alumniforum.constitution.service.interfa;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.constitution.dto.ConstitutionForm;
import com.esm.alumniforum.constitution.dto.ConstitutionResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public interface ConstitutionService {
    ConstitutionResponse save(ConstitutionForm conForm, UserPrincipal principal) throws IOException;
    ConstitutionResponse saveForOrg(ConstitutionForm conForm, UserPrincipal principal) throws IOException;
    ConstitutionResponse update(ConstitutionForm conForm, UserPrincipal principal) throws IOException;
    ConstitutionResponse findById(String id);
    PagedResponse<ConstitutionResponse> findAll(PageRequest pageRequest);
    void delete(String id);
    List<ConstitutionResponse> findAllByMyOrg(String orgId);
    List<ConstitutionResponse> findAllByOrg(String orgId);

}

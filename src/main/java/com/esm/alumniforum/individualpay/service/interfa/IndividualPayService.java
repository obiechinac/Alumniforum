package com.esm.alumniforum.individualpay.service.interfa;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.individualpay.dto.IndividualPayForm;
import com.esm.alumniforum.individualpay.dto.IndividualPayResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

public interface IndividualPayService {
    IndividualPayResponse save(IndividualPayForm individualPayForm, UserPrincipal principal);

    IndividualPayResponse update(IndividualPayForm individualPayForm, UserPrincipal principal);
    IndividualPayResponse findById(String id);
    PagedResponse<IndividualPayResponse> findAll(PageRequest pageRequest);
    void delete(String id);

    PagedResponse<IndividualPayResponse> findAllByPaymentId(PageRequest pageRequest, String paymentId);
}

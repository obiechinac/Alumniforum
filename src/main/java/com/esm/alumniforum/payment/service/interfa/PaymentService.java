package com.esm.alumniforum.payment.service.interfa;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.payment.dto.PaymentForm;
import com.esm.alumniforum.payment.dto.PaymentResponse;
import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.data.domain.PageRequest;

public interface PaymentService {
    PaymentResponse save(PaymentForm payform, UserPrincipal principal);

    PaymentResponse update(PaymentForm payForm, UserPrincipal principal);
    PaymentResponse findById(String id);
    PagedResponse<PaymentResponse> findAll(PageRequest pageRequest);
    void delete(String id);

}

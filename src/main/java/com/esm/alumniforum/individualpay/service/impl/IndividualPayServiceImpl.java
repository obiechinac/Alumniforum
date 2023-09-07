package com.esm.alumniforum.individualpay.service.impl;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.individualpay.dto.IndividualPayForm;
import com.esm.alumniforum.individualpay.dto.IndividualPayResponse;
import com.esm.alumniforum.individualpay.model.IndividualPay;
import com.esm.alumniforum.individualpay.repository.IndividualPayRepository;
import com.esm.alumniforum.individualpay.service.interfa.IndividualPayService;
import com.esm.alumniforum.member.repository.MemberRepository;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.payment.repository.PaymentRepository;
import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class IndividualPayServiceImpl implements IndividualPayService {
    private static final String MSG = "Individual Pay cannot be found";
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private IndividualPayRepository individualPayRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public IndividualPayResponse save(IndividualPayForm individualPayForm, UserPrincipal principal) {
        IndividualPay individualPay = new IndividualPay();

        individualPay.setIsDeleted(false);
        individualPay.setOrganisation(organisationRepository.findById(individualPayForm.getOrganisationId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",individualPayForm.getOrganisationId())));
        individualPay.setPayment(paymentRepository.findById(individualPayForm.getPaymentId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",individualPayForm.getPaymentId())));
        individualPay.setMember(memberRepository.findById(individualPayForm.getMemberId()).orElseThrow(()->new ResourceNotFoundException("Member not found with: ","id",individualPayForm.getMemberId())));
        individualPay.setCreatedDate(LocalDate.now());
        individualPay.setCreatedBy(principal.getEmail());
        IndividualPay savedPay = this.individualPayRepository.save(individualPay);
        IndividualPayResponse response = IndividualPayResponse.builder()
                .id(savedPay.getId())
                        .paymentId(savedPay.getPayment().getId())
                            .paymentNature(savedPay.getPayment().getPaymentNature().toString())
                            .paidAmount(savedPay.getPaidAmount())
                            .requiredAmount(savedPay.getRequiredAmount())
                . build();
      return response;
    }

    @Override
    public IndividualPayResponse update(IndividualPayForm payForm, UserPrincipal principal) {
        IndividualPay payment = this.individualPayRepository.findById(payForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", payForm.getId()));
        payment.setModifiedDate(LocalDate.now());
        payment.setModifiedBy(principal.getEmail());
        payment.setPaidAmount(payForm.getPaidAmount());
        individualPayRepository.save(payment);

        IndividualPayResponse response = IndividualPayResponse.builder()
                .id(payment.getId())
                .paymentId(payment.getPayment().getId())
                .paymentNature(payment.getPayment().getPaymentNature().toString())
                .paidAmount(payment.getPaidAmount())
                .requiredAmount(payment.getRequiredAmount())
                .paidBy(payment.getMember().getFirstName()+" "
                        +payment.getMember().getLastName())
                . build();
        return response;
    }

    @Override
    public IndividualPayResponse findById(String id) {
        IndividualPay payment = this.individualPayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));

        IndividualPayResponse response = IndividualPayResponse.builder()
                .id(payment.getId())
                .paymentId(payment.getPayment().getId())
                .paymentNature(payment.getPayment().getPaymentNature().toString())
                .paidAmount(payment.getPaidAmount())
                .requiredAmount(payment.getRequiredAmount())
                .paidBy(payment.getMember().getFirstName()+" "
                        +payment.getMember().getLastName())
                . build();
        return response;
    }

    @Override
    public PagedResponse<IndividualPayResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<IndividualPay> pays = this.individualPayRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),pays);
    }

    @Override
    public void delete(String id) {
        IndividualPay pay = this.individualPayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        pay.setIsDeleted(true);
        this.individualPayRepository.save(pay);

    }

    @Override
    public PagedResponse<IndividualPayResponse> findAllByPaymentId(PageRequest pageRequest, String paymentId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<IndividualPay> pays = this.individualPayRepository.findAllByPaymentId(pageRequest,paymentId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),pays);
    }

    public PagedResponse<IndividualPayResponse> getPagedResponse(int page, int size, Page<IndividualPay> payments){

if(payments.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),payments.getNumber(),payments.getSize(), payments.getTotalElements(), payments.getTotalPages(),
            payments.isLast(), payments.isFirst(), payments.isEmpty());
}
    List<IndividualPayResponse> responses = new ArrayList<>();

    payments.forEach(pay->{
        try {
            IndividualPayResponse response = IndividualPayResponse.builder()
                    .id(pay.getId())
                    .paymentId(pay.getPayment().getId())
                    .paymentNature(pay.getPayment().getPaymentNature().toString())
                    .paidAmount(pay.getPaidAmount())
                    .requiredAmount(pay.getRequiredAmount())
                    .paidBy(pay.getMember().getFirstName()+" "
                            +pay.getMember().getLastName())
                    . build();
            responses.add(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, payments.getNumber(),
                payments.getSize(), payments.getTotalElements(), payments.getTotalPages(), payments.isLast(), payments.isFirst(), payments.isEmpty());
    }
}

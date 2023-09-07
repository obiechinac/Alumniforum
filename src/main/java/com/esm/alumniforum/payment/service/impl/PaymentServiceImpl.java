package com.esm.alumniforum.payment.service.impl;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.activity.model.Activity;
import com.esm.alumniforum.activity.repository.ActivityRepository;
import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.config.UserIdentity;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.payment.dto.PaymentForm;
import com.esm.alumniforum.payment.dto.PaymentResponse;
import com.esm.alumniforum.payment.model.Payment;
import com.esm.alumniforum.payment.repository.PaymentRepository;
import com.esm.alumniforum.payment.service.interfa.PaymentService;
import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
public class PaymentServiceImpl implements PaymentService {
    private static final String MSG = "Payment cannot be found";
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
@Autowired
private UsersRepository usersRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserIdentity userIdentity;

    @Override
    public PaymentResponse save(PaymentForm paymentForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Payment payment = modelMapper.map(paymentForm,Payment.class);
        payment.setIsDeleted(false);
        payment.setOrganisation(organisationRepository.findById(paymentForm.getOrganisationId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",paymentForm.getOrganisationId())));
        payment.setActivity(activityRepository.findById(paymentForm.getActivityId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",paymentForm.getActivityId())));
//        payment.setUsers(usersRepository.findById(paymentForm.getUsersId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",paymentForm.getUsersId())));
        payment.setCreatedDate(LocalDate.now());
        payment.setCreatedBy(userIdentity.getEmail());
        Payment savedPay = this.paymentRepository.save(payment);
      return modelMapper.map(savedPay,PaymentResponse.class);
    }

    @Override
    public PaymentResponse update(PaymentForm payForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Payment payment = this.paymentRepository.findById(payForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", payForm.getId()));
        Payment pay;
        pay = modelMapper.map(payForm,Payment.class);
        pay.setId(payment.getId());
        pay.setIsDeleted(false);
        pay.setModifiedDate(LocalDate.now());
        pay.setModifiedBy(principal.getEmail());
//        pay.setUsers(payment.getUsers());
        pay.setOrganisation(payment.getOrganisation());
        pay.setPaymentNature(payment.getPaymentNature());
        pay.setPaymentType(payment.getPaymentType());
        pay.setActivity(payment.getActivity());
        Payment savedPay= this.paymentRepository.save(pay);
      return modelMapper.map(savedPay,PaymentResponse.class);
    }

    @Override
    public PaymentResponse findById(String id) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        ModelMapper modelMapper= new ModelMapper();
      return modelMapper.map(payment,PaymentResponse.class);

    }

    @Override
    public PagedResponse<PaymentResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Payment> pays = this.paymentRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),pays);
    }

    @Override
    public void delete(String id) {
        Payment pay = this.paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        pay.setIsDeleted(true);
        this.paymentRepository.save(pay);

    }

    public PagedResponse<PaymentResponse> getPagedResponse(int page, int size, Page<Payment> payments){

if(payments.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),payments.getNumber(),payments.getSize(), payments.getTotalElements(), payments.getTotalPages(),
            payments.isLast(), payments.isFirst(), payments.isEmpty());
}
    List<PaymentResponse> responses = new ArrayList<>();
    ModelMapper mapper = new ModelMapper();

    payments.forEach(pay->{
        try {
            responses.add(mapper.map(pay, PaymentResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, payments.getNumber(),
                payments.getSize(), payments.getTotalElements(), payments.getTotalPages(), payments.isLast(), payments.isFirst(), payments.isEmpty());
    }
}

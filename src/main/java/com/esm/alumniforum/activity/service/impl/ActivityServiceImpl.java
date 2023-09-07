package com.esm.alumniforum.activity.service.impl;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.activity.model.Activity;
import com.esm.alumniforum.activity.repository.ActivityRepository;
import com.esm.alumniforum.activity.service.interfa.ActivityService;
import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.security.UserPrincipal;
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
public class ActivityServiceImpl implements ActivityService {
    private static final String MSG = "Activity cannot be found";
    @Autowired
    private ActivityRepository actRepository;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Override
    public ActivityResponse save(ActivityForm actForm, UserPrincipal principal) {
        Activity activity = new Activity();
        activity.setOrganisation(principal.getOrganisation());
        activity.setIsDeleted(false);
        activity.setCreatedDate(LocalDate.now());
        activity.setCreatedBy(principal.getEmail());
        activity.setActivityType(actForm.getActivityType());
        activity.setDate(actForm.getDate());
        activity.setDescription(actForm.getDescription());
        activity.setPaymentNature(actForm.getPaymentNature());
        activity.setName(actForm.getName());
        activity.setPaymentType(actForm.getPaymentType());
        Activity savedAct = this.actRepository.save(activity);

        ActivityResponse response = ActivityResponse.builder()
                .activityType(savedAct.getActivityType().toString())
                .createdDate(savedAct.getCreatedDate())
                .description(savedAct.getDescription())
                .name(savedAct.getName())
                .organisation(savedAct.getOrganisation())
                .paymentNature(savedAct.getPaymentNature().toString())
                .build();
      return response;
    }

    @Override
    public ActivityResponse update(ActivityForm actForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Activity activity = this.actRepository.findById(actForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", actForm.getId()));
        activity.setModifiedDate(LocalDate.now());
        activity.setModifiedBy(principal.getEmail());
        activity.setActivityType(actForm.getActivityType());
        activity.setDate(actForm.getDate());
        activity.setDescription(actForm.getDescription());
        activity.setPaymentNature(actForm.getPaymentNature());
        activity.setName(actForm.getName());
        activity.setPaymentType(actForm.getPaymentType());
        Activity savedAct= this.actRepository.save(activity);

        ActivityResponse response = ActivityResponse.builder()
                .activityType(savedAct.getActivityType().toString())
                .createdDate(savedAct.getCreatedDate())
                .description(savedAct.getDescription())
                .name(savedAct.getName())
                .organisation(savedAct.getOrganisation())
                .paymentNature(savedAct.getPaymentNature().toString())
                .build();
        return response;
    }

    @Override
    public ActivityResponse findById(String id) {
        Activity activity = this.actRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        ModelMapper modelMapper= new ModelMapper();
      return modelMapper.map(activity,ActivityResponse.class);

    }

    @Override
    public PagedResponse<ActivityResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }

    @Override
    public void delete(String id) {
        Activity activity = this.actRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        activity.setIsDeleted(true);
        this.actRepository.save(activity);

    }

    @Override
    public PagedResponse<ActivityResponse> findAllUnpaidByOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllUnpaidByOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }

    @Override
    public PagedResponse<ActivityResponse> findAllUnpaidByMyOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllUnpaidByMyOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }
    @Override
    public PagedResponse<ActivityResponse> findAllVoluntaryByOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllVoluntaryByOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }
    @Override
    public PagedResponse<ActivityResponse> findAllVoluntaryByMyOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllVoluntaryByMyOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }

    @Override
    public PagedResponse<ActivityResponse> findAllCompulsoryByMyOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllCompulsoryByMyOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }
    @Override
    public PagedResponse<ActivityResponse> findAllCompulsoryByOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Activity> orgs = this.actRepository.findAllCompulsoryByOrg(pageRequest,orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }



    public PagedResponse<ActivityResponse> getPagedResponse(int page, int size, Page<Activity> acts){

if(acts.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),acts.getNumber(),acts.getSize(), acts.getTotalElements(), acts.getTotalPages(),
            acts.isLast(), acts.isFirst(), acts.isEmpty());
}
    List<ActivityResponse> responses = new ArrayList<>();
    ModelMapper mapper = new ModelMapper();

    acts.forEach(act->{
        try {
            responses.add(mapper.map(act, ActivityResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, acts.getNumber(),
                acts.getSize(), acts.getTotalElements(), acts.getTotalPages(), acts.isLast(), acts.isFirst(), acts.isEmpty());
    }
}

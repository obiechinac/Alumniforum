package com.esm.alumniforum.organisation.service.impl;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.config.UserIdentity;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.organisation.service.interfa.OrganisationService;
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
public class OrganisationServiceImpl implements OrganisationService {
    private static final String MSG = "Organisation cannot be found";
    @Autowired
    private OrganisationRepository orgRepository;
//    @Autowired
//    @Qualifier("customUserPrincipal")
//    private UserPrincipal principal;

    @Autowired
    private UserIdentity userIdentity;

    @Override
    public OrganisationResponse save(OrganisationForm orgForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Organisation  organisation = modelMapper.map(orgForm,Organisation.class);
        organisation.setIsDeleted(false);
        organisation.setCreatedDate(LocalDate.now());
        organisation.setCreatedBy(userIdentity.getEmail());
        Organisation savedOrg = this.orgRepository.save(organisation);
      return modelMapper.map(savedOrg,OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse save(OrganisationForm orgForm) {
        ModelMapper modelMapper= new ModelMapper();
        Organisation  organisation = modelMapper.map(orgForm,Organisation.class);
        organisation.setIsDeleted(false);
        organisation.setCreatedDate(LocalDate.now());
//        organisation.setCreatedBy(principal.getEmail());
        Organisation savedOrg = this.orgRepository.save(organisation);
        return modelMapper.map(savedOrg,OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse update(OrganisationForm orgForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Organisation organisation = this.orgRepository.findById(orgForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", orgForm.getId()));
         Organisation org;
        org = modelMapper.map(orgForm,Organisation.class);
        org.setId(organisation.getId());
        org.setIsDeleted(false);
        org.setModifiedDate(LocalDate.now());
        org.setModifiedBy(userIdentity.getEmail()+"From Identity");
        Organisation savedOrg = this.orgRepository.save(org);
      return modelMapper.map(savedOrg,OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse findById(String id) {
        Organisation organisation = this.orgRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        ModelMapper modelMapper= new ModelMapper();
      return modelMapper.map(organisation,OrganisationResponse.class);

    }

    @Override
    public PagedResponse<OrganisationResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Organisation> orgs = this.orgRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),orgs);
    }

    @Override
    public void delete(String id) {
        Organisation organisation = this.orgRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        organisation.setIsDeleted(true);
        this.orgRepository.save(organisation);

    }

    public PagedResponse<OrganisationResponse> getPagedResponse(int page, int size, Page<Organisation> orgs){

if(orgs.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),orgs.getNumber(),orgs.getSize(), orgs.getTotalElements(), orgs.getTotalPages(),
            orgs.isLast(), orgs.isFirst(), orgs.isEmpty());
}
    List<OrganisationResponse> responses = new ArrayList<>();
    ModelMapper mapper = new ModelMapper();

    orgs.forEach(org->{
        try {
            responses.add(mapper.map(org, OrganisationResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, orgs.getNumber(),
                orgs.getSize(), orgs.getTotalElements(), orgs.getTotalPages(), orgs.isLast(), orgs.isFirst(), orgs.isEmpty());
    }
}

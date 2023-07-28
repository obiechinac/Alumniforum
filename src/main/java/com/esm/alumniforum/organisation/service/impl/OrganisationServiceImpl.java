package com.esm.alumniforum.organisation.service.impl;

import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.organisation.service.interfa.OrganisationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class OrganisationServiceImpl implements OrganisationService {
    private static final String MSG = "Organisation cannot be found";
    @Autowired
    private OrganisationRepository orgRepository;
    @Override
    public OrganisationResponse save(OrganisationForm orgForm) {
        ModelMapper modelMapper= new ModelMapper();
        Organisation  organisation = modelMapper.map(orgForm,Organisation.class);
        organisation.setIsDeleted(false);
        organisation.setCreatedDate(LocalDate.now());
        Organisation savedOrg = this.orgRepository.save(organisation);
//        OrganisationResponse response = modelMapper.map(savedOrg,OrganisationResponse.class);
        return modelMapper.map(savedOrg,OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse update(OrganisationForm orgForm) {
        ModelMapper modelMapper= new ModelMapper();
        Organisation organisation_ = this.orgRepository.findById(orgForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", orgForm.getId()));
        Organisation  organisation = modelMapper.map(orgForm,Organisation.class);
        organisation.setIsDeleted(false);
        organisation.setCreatedDate(LocalDate.now());
        Organisation savedOrg = this.orgRepository.save(organisation);
//        OrganisationResponse response = modelMapper.map(savedOrg,OrganisationResponse.class);
        return modelMapper.map(savedOrg,OrganisationResponse.class);
    }

    @Override
    public OrganisationResponse findById() {
        return null;
    }

    @Override
    public Page<OrganisationResponse> findAll(PageRequest pageRequest) {
        return null;
    }
}

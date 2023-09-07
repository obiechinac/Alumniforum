package com.esm.alumniforum.constitution.service.impl;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.config.UserIdentity;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.constitution.dto.ConstitutionForm;
import com.esm.alumniforum.constitution.dto.ConstitutionResponse;
import com.esm.alumniforum.constitution.model.Constitution;
import com.esm.alumniforum.constitution.repository.ConstitutionRepository;
import com.esm.alumniforum.constitution.service.interfa.ConstitutionService;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ConstitutionServiceImpl implements ConstitutionService {
    private static final String MSG = "Organisation cannot be found";
    @Autowired
    private ConstitutionRepository constitutionRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
//    @Autowired
//    @Qualifier("customUserPrincipal")
//    private UserPrincipal principal;

    @Autowired
    private UserIdentity userIdentity;

    @Override
    public ConstitutionResponse save(ConstitutionForm conForm, UserPrincipal principal) throws IOException {
        ModelMapper modelMapper= new ModelMapper();
//        Constitution  constitution = modelMapper.map(conForm, Constitution.class);
        Constitution constitution = new Constitution();
        constitution.setDescription(conForm.getDescription());
       // constitution.setOrganisation(organisationRepository.findById(conForm.getOrganisationId()).orElseThrow(()->new ResourceNotFoundException("organisation not found with: ", "", conForm.getOrganisationId())));
        constitution.setOrganisation(principal.getOrganisation());
        constitution.setFile(conForm.getFile().getBytes());
        constitution.setIsDeleted(false);
        constitution.setCreatedDate(LocalDate.now());
        constitution.setCreatedBy(principal.getEmail());
        Constitution savedCon = this.constitutionRepository.save(constitution);
        ConstitutionResponse response = ConstitutionResponse.builder()
                .createdDate(savedCon.getCreatedDate())
                .description(savedCon.getDescription())
                .file(savedCon.getFile())
                .build();
      return modelMapper.map(savedCon,ConstitutionResponse.class);
    }


    @Override
    public ConstitutionResponse update(ConstitutionForm conForm, UserPrincipal principal) throws IOException {
        ModelMapper modelMapper= new ModelMapper();
         Constitution constitution = new Constitution();
        Constitution con = this.constitutionRepository.findById(conForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", conForm.getId()));

        Organisation organisation = this.organisationRepository.findById(conForm.getOrganisationId()).orElseThrow(() -> new ResourceNotFoundException("organisation not found","id", conForm.getOrganisationId()));
//        constitution = modelMapper.map(conForm,Constitution.class);
        constitution.setFile(conForm.getFile().getBytes());
        constitution.setId(con.getId());
        constitution.setIsDeleted(false);
        constitution.setModifiedDate(LocalDate.now());
        constitution.setModifiedBy(principal.getEmail());
        Constitution savedCon = this.constitutionRepository.save(constitution);
      return modelMapper.map(savedCon,ConstitutionResponse.class);
    }

    @Override
    public ConstitutionResponse findById(String id) {
        Constitution constitution = this.constitutionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        ModelMapper modelMapper= new ModelMapper();
      return modelMapper.map(constitution,ConstitutionResponse.class);

    }

    @Override
    public PagedResponse<ConstitutionResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Constitution> cons = this.constitutionRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),cons);
    }

    @Override
    public void delete(String id) {
        Constitution constitution = this.constitutionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        constitution.setIsDeleted(true);
        this.constitutionRepository.save(constitution);

    }

    public PagedResponse<ConstitutionResponse> getPagedResponse(int page, int size, Page<Constitution> constitutions){

if(constitutions.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),constitutions.getNumber(),constitutions.getSize(), constitutions.getTotalElements(), constitutions.getTotalPages(),
            constitutions.isLast(), constitutions.isFirst(), constitutions.isEmpty());
}
    List<ConstitutionResponse> responses = new ArrayList<>();
    ModelMapper mapper = new ModelMapper();

    constitutions.forEach(con->{
        try {
            responses.add(mapper.map(con, ConstitutionResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, constitutions.getNumber(),
                constitutions.getSize(), constitutions.getTotalElements(), constitutions.getTotalPages(), constitutions.isLast(), constitutions.isFirst(), constitutions.isEmpty());
    }
}

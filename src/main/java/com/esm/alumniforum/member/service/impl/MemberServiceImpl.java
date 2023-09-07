package com.esm.alumniforum.member.service.impl;
import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.config.UserIdentity;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.member.dto.MemberForm;
import com.esm.alumniforum.member.dto.MemberResponse;
import com.esm.alumniforum.member.model.Member;
import com.esm.alumniforum.member.repository.MemberRepository;
import com.esm.alumniforum.member.service.interfa.MemberService;
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
public class MemberServiceImpl implements MemberService {
    private static final String MSG = "Member cannot be found";
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Override
    public MemberResponse save(MemberForm membForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Member member = modelMapper.map(membForm,Member.class);
        if(membForm.getOrganisationId()!=""&&membForm.getOrganisationId()!=null){
            member.setOrganisation(organisationRepository.findById(membForm.getOrganisationId()).orElseThrow(()->new ResourceNotFoundException(MSG,"id",membForm.getOrganisationId())));
        }
        member.setOrganisation(principal.getOrganisation());
        member.setIsDeleted(false);
        member.setCreatedDate(LocalDate.now());
        member.setCreatedBy(principal.getEmail());
        Member savedMemb = this.memberRepository.save(member);
      return modelMapper.map(savedMemb,MemberResponse.class);
    }

    @Override
    public MemberResponse update(MemberForm memForm, UserPrincipal principal) {
        ModelMapper modelMapper= new ModelMapper();
        Member member = this.memberRepository.findById(memForm.getId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", memForm.getId()));
        Member member1;
        member1 = modelMapper.map(memForm,Member.class);
        member1.setId(member.getId());
        member1.setIsDeleted(false);
        member1.setModifiedDate(LocalDate.now());
        member1.setModifiedBy(principal.getEmail());
        Member savedMem= this.memberRepository.save(member1);
      return modelMapper.map(savedMem,MemberResponse.class);
    }

    @Override
    public MemberResponse findById(String id) {
        Member member = this.memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        return new ModelMapper().map(member,MemberResponse.class);

    }

    @Override
    public PagedResponse<MemberResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Member> members = this.memberRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),members);
    }

    @Override
    public void delete(String id) {
        Member member = this.memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        member.setIsDeleted(true);
        this.memberRepository.save(member);

    }

    @Override
    public PagedResponse<MemberResponse> findAllByOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Member> members = this.memberRepository.findAllByOrg(pageRequest, orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),members);
    }

    @Override
    public PagedResponse<MemberResponse> findAllByMyOrg(PageRequest pageRequest, String orgId) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Member> members = this.memberRepository.findAllByMyOrg(pageRequest, orgId);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),members);
    }

    public PagedResponse<MemberResponse> getPagedResponse(int page, int size, Page<Member> members){

if(members.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),members.getNumber(),members.getSize(), members.getTotalElements(), members.getTotalPages(),
            members.isLast(), members.isFirst(), members.isEmpty());
}
    List<MemberResponse> responses = new ArrayList<>();
    members.forEach(mem->{
        try {
            responses.add(new ModelMapper().map(mem, MemberResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, members.getNumber(),
                members.getSize(), members.getTotalElements(), members.getTotalPages(), members.isLast(), members.isFirst(), members.isEmpty());
    }
}

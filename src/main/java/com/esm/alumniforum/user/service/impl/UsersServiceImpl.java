package com.esm.alumniforum.user.service.impl;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.BadRequestException;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.organisation.model.Organisation;
import com.esm.alumniforum.organisation.repository.OrganisationRepository;
import com.esm.alumniforum.security.CurrentUser;
import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.dto.*;
import com.esm.alumniforum.user.model.Profile;
import com.esm.alumniforum.user.model.Users;
import com.esm.alumniforum.user.repository.ProfileRepository;
import com.esm.alumniforum.user.repository.UsersRepository;
import com.esm.alumniforum.user.service.interfa.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    private static final String MSG = "User cannot be found";
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private OrganisationRepository organisationRepository;
    @Override
    public UsersResponse save(UsersForm usersForm, UserPrincipal principal) {
        if (this.usersRepository.existsByEmail(usersForm.getEmail())) {
            throw new BadRequestException("Email is already taken, choose another");
        }
        Organisation org = organisationRepository.findById(principal.getOrganisation().getId()).orElseThrow(()-> new ResourceNotFoundException("Organisation with Id ", principal.getOrganisation().getId(), "not found"));
        ModelMapper modelMapper= new ModelMapper();
        Users users = modelMapper.map(usersForm,Users.class);
        String password = passwordEncoder.encode(users.getPassword());
        users.setPassword(password);
        users.setOrganisation(org);
        users.setIsDeleted(false);
        users.setCreatedBy(principal.getEmail());
        users.setCreatedDate(LocalDate.now());
        Users savedUser = this.usersRepository.save(users);
        UsersResponse response = modelMapper.map(savedUser,UsersResponse.class);;
        response.setOrgId(savedUser.getOrganisation().getId());
        response.setOrgName(savedUser.getOrganisation().getOrgName());
        response.setProfileResponse(modelMapper.map(savedUser.getProfile(),ProfileResponse.class));
          return response;
    }

    @Override
    public ProfileResponse update(ProfileForm form) {
        ModelMapper modelMapper= new ModelMapper();
        Users user = this.usersRepository.findById(form.getUserId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", form.getUserId()));
        TypeMap<ProfileForm,Profile> typeMap=  modelMapper.createTypeMap(ProfileForm.class,Profile.class);
        typeMap.addMappings(mapper -> mapper.skip(Profile::setProfilePictureUrl));

        Profile profile = modelMapper.map(form, Profile.class);
        profile.setUserId(user.getId());
       Profile savedProfile=  this.profileRepository.save(profile);
        user.setProfile(savedProfile);
        this.usersRepository.save(user);
        return modelMapper.map(savedProfile,ProfileResponse.class);
    }

    @Override
    public ImageResponse updateProfilePicture(ImageForm form) {
        Users user = this.usersRepository.findById(form.getUserId()).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", form.getUserId()));

        Profile profile = user.getProfile();
        profile.setProfilePictureUrl(form.getImageUrl());
        this.profileRepository.save(profile);
        return new ImageResponse(profile.getProfilePictureUrl(),profile.getUserId());
    }



    @Override
    public UsersResponse findById(String id) {
        Users users = this.usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        ModelMapper modelMapper= new ModelMapper();
        return modelMapper.map(users,UsersResponse.class);

    }

    @Override
    public UsersResponse findByEmail(String email) {
        Users users = this.usersRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(MSG,"email", email));
        ModelMapper modelMapper= new ModelMapper();
        return modelMapper.map(users,UsersResponse.class);
    }

    @Override
    public PagedResponse<UsersResponse> findAll(PageRequest pageRequest) {
        Constant.validatePageNumberAndSize(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Users> users = this.usersRepository.findAll(pageRequest);
        return getPagedResponse(pageRequest.getPageNumber(), pageRequest.getPageSize(),users);
    }

    @Override
    public void delete(String id) {
        Users users = this.usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG,"id", id));
        users.setIsDeleted(true);
        this.usersRepository.save(users);

    }

    @Override
    public boolean existsByEmail(String email) {
        return this.usersRepository.existsByEmail(email);
    }

    public PagedResponse<UsersResponse> getPagedResponse(int page, int size, Page<Users> users){

if(users.isEmpty()){

    return new PagedResponse<>(Collections.emptyList(),users.getNumber(),users.getSize(), users.getTotalElements(), users.getTotalPages(),
            users.isLast(), users.isFirst(), users.isEmpty());
}
    List<UsersResponse> responses = new ArrayList<>();
    ModelMapper mapper = new ModelMapper();

        users.forEach(user->{
        try {
            responses.add(mapper.map(user, UsersResponse.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });


        return new PagedResponse<>(responses, users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast(), users.isFirst(), users.isEmpty());
    }
}

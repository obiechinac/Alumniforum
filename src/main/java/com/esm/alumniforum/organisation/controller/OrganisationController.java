package com.esm.alumniforum.organisation.controller;

import com.esm.alumniforum.common.response.PagedResponse;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ForbiddenException;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.exceptions.UnauthorizedException;
import com.esm.alumniforum.organisation.dto.OrganisationForm;
import com.esm.alumniforum.organisation.dto.OrganisationResponse;
import com.esm.alumniforum.organisation.service.interfa.OrganisationService;
import com.esm.alumniforum.security.CurrentUser;
import com.esm.alumniforum.security.UserPrincipal;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organisation/")
public class OrganisationController {
    @Autowired
    private OrganisationService orgService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<OrganisationResponse> create(@RequestBody OrganisationForm organisationForm, @CurrentUser UserPrincipal principal){

    return new ResponseEntity<>(this.orgService.save(organisationForm, principal), HttpStatus.CREATED);
}

//    @PostMapping("create")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<OrganisationResponse> create(@RequestBody OrganisationForm organisationForm){
//
//        return new ResponseEntity<>(this.orgService.save(organisationForm), HttpStatus.CREATED);
//    }
    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody OrganisationForm organisationForm, @CurrentUser UserPrincipal principal){

        return new ResponseEntity<>(this.orgService.update(organisationForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(orgService.findById(id), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

//            PagedResponse<OrganisationResponse> pagedResponse = orgService.findAll(pageRequest);
            return new ResponseEntity<>(orgService.findAll(pageRequest), headers, HttpStatus.OK);


    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            orgService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}

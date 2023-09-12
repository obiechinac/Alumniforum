package com.esm.alumniforum.constitution.controller;

import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.constitution.dto.ConstitutionForm;
import com.esm.alumniforum.constitution.dto.ConstitutionResponse;
import com.esm.alumniforum.constitution.service.interfa.ConstitutionService;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
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

import java.io.IOException;

@RestController
@RequestMapping("/constitution/")
public class ConstitutionController {
    @Autowired
    private ConstitutionService constitutionService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<ConstitutionResponse> create(@RequestBody ConstitutionForm constitutionForm, @CurrentUser UserPrincipal principal) throws IOException {

    return new ResponseEntity<>(this.constitutionService.save(constitutionForm, principal), HttpStatus.CREATED);
}

    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody ConstitutionForm constitutionForm, @CurrentUser UserPrincipal principal) throws IOException {

        return new ResponseEntity<>(this.constitutionService.update(constitutionForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(constitutionService.findById(id), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

            return new ResponseEntity<>(constitutionService.findAll(pageRequest), headers, HttpStatus.OK);


    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            constitutionService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all/{orgId}")
    @PreAuthorize("hasAnyAuthority('staff','owner')")
    public ResponseEntity<?> findAllCompulsoryByOrgPageable(@PathVariable(name = "orgId") String orgId){
        return new ResponseEntity<>(constitutionService.findAllByOrg(orgId),HttpStatus.OK);
    }

    @GetMapping("myorg/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllByMyOrgPageable(@CurrentUser UserPrincipal principal){
        return new ResponseEntity<>(constitutionService.findAllByMyOrg(principal.getOrganisation().getId()),HttpStatus.OK);
    }
}

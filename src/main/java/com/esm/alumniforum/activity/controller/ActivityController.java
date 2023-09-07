package com.esm.alumniforum.activity.controller;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.activity.service.interfa.ActivityService;
import com.esm.alumniforum.constant.Constant;
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

@RestController
@RequestMapping("/activity/")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<ActivityResponse> create(@RequestBody ActivityForm activityForm, @CurrentUser UserPrincipal principal){

    return new ResponseEntity<>(this.activityService.save(activityForm, principal), HttpStatus.CREATED);
}

    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody ActivityForm activityForm, @CurrentUser UserPrincipal principal){

        return new ResponseEntity<>(this.activityService.update(activityForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(activityService.findById(id), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
            return new ResponseEntity<>(activityService.findAll(pageRequest), headers, HttpStatus.OK);


    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            activityService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("unpaid/all/{orgId}")
    @PreAuthorize("hasAnyAuthority('staff','owner')")
    public ResponseEntity<?> findAllUnpaidByOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,@PathVariable(name = "orgId") String orgId){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllUnpaidByOrg(pageRequest,orgId), headers, HttpStatus.OK);
    }

    @GetMapping("unpaid/myorg/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllUnpaidByMyPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size, UserPrincipal principal){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllUnpaidByMyOrg(pageRequest,principal.getOrganisation().getId()), headers, HttpStatus.OK);
    }
    @GetMapping("voluntary/all/{orgId}")
    @PreAuthorize("hasAnyAuthority('staff','owner')")
    public ResponseEntity<?> findAllVoluntaryByOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,@PathVariable(name = "orgId") String orgId){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllVoluntaryByOrg(pageRequest,orgId), headers, HttpStatus.OK);
    }

    @GetMapping("voluntary/myorg/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllVoluntaryByMyOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size, UserPrincipal principal){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllVoluntaryByMyOrg(pageRequest,principal.getOrganisation().getId()), headers, HttpStatus.OK);
    }
    @GetMapping("compulsory/all/{orgId}")
    @PreAuthorize("hasAnyAuthority('staff','owner')")
    public ResponseEntity<?> findAllCompulsoryByOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,@PathVariable(name = "orgId") String orgId){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllCompulsoryByOrg(pageRequest,orgId), headers, HttpStatus.OK);
    }

    @GetMapping("compulsory/myorg/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllCompulsoryByMyOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size, UserPrincipal principal){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(activityService.findAllCompulsoryByMyOrg(pageRequest,principal.getOrganisation().getId()), headers, HttpStatus.OK);
    }
}

package com.esm.alumniforum.member.controller;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.activity.dto.ActivityResponse;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.member.dto.MemberForm;
import com.esm.alumniforum.member.dto.MemberResponse;
import com.esm.alumniforum.member.service.interfa.MemberService;
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
@RequestMapping("/member/")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<MemberResponse> create(@RequestBody MemberForm memberForm, @CurrentUser UserPrincipal principal){

    return new ResponseEntity<>(this.memberService.save(memberForm, principal), HttpStatus.CREATED);
}

    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody MemberForm memberForm, @CurrentUser UserPrincipal principal){

        return new ResponseEntity<>(this.memberService.update(memberForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(memberService.findById(id), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

            return new ResponseEntity<>(memberService.findAll(pageRequest), headers, HttpStatus.OK);

    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            memberService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all/{orgId}")
    @PreAuthorize("hasAnyAuthority('staff','owner')")
    public ResponseEntity<?> findAllByOrgPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,@PathVariable(name = "orgId") String orgId){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(memberService.findAllByOrg(pageRequest,orgId), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllByMyPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size, UserPrincipal principal){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(memberService.findAllByMyOrg(pageRequest,principal.getOrganisation().getId()), headers, HttpStatus.OK);
    }
}

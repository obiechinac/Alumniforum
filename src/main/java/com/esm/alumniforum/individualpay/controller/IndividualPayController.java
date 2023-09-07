package com.esm.alumniforum.individualpay.controller;

import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.individualpay.dto.IndividualPayForm;
import com.esm.alumniforum.individualpay.dto.IndividualPayResponse;
import com.esm.alumniforum.individualpay.service.interfa.IndividualPayService;
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
@RequestMapping("/individualpay/")
public class IndividualPayController {
    @Autowired
    private IndividualPayService individualPayService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<IndividualPayResponse> create(@RequestBody IndividualPayForm individualPayForm, @CurrentUser UserPrincipal principal){

    return new ResponseEntity<>(this.individualPayService.save(individualPayForm, principal), HttpStatus.CREATED);
}

    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody IndividualPayForm individualPayForm, @CurrentUser UserPrincipal principal){

        return new ResponseEntity<>(this.individualPayService.update(individualPayForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(individualPayService.findById(id), headers, HttpStatus.OK);
    }


    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

            return new ResponseEntity<>(individualPayService.findAll(pageRequest), headers, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            individualPayService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all/{paymentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllByPaymentIdPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,@RequestParam(name = "paymentId", required = true) String paymentId){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

        return new ResponseEntity<>(individualPayService.findAllByPaymentId(pageRequest, paymentId), headers, HttpStatus.OK);
    }
}

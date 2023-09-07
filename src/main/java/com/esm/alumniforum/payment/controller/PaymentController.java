package com.esm.alumniforum.payment.controller;

import com.esm.alumniforum.activity.dto.ActivityForm;
import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.payment.dto.PaymentForm;
import com.esm.alumniforum.payment.dto.PaymentResponse;
import com.esm.alumniforum.payment.service.interfa.PaymentService;
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
@RequestMapping("/payment/")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("create")
    @PreAuthorize("isAuthenticated()")
   public ResponseEntity<PaymentResponse> create(@RequestBody PaymentForm paymentForm, @CurrentUser UserPrincipal principal){

    return new ResponseEntity<>(this.paymentService.save(paymentForm, principal), HttpStatus.CREATED);
}

    @PostMapping("update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@RequestBody PaymentForm paymentForm, @CurrentUser UserPrincipal principal){

        return new ResponseEntity<>(this.paymentService.update(paymentForm, principal), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(paymentService.findById(id), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);

//            PagedResponse<OrganisationResponse> pagedResponse = paymentService.findAll(pageRequest);
            return new ResponseEntity<>(paymentService.findAll(pageRequest), headers, HttpStatus.OK);


    }

    @GetMapping("delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            paymentService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}

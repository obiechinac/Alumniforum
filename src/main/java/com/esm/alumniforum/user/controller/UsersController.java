package com.esm.alumniforum.user.controller;

import com.esm.alumniforum.constant.Constant;
import com.esm.alumniforum.exceptions.BadRequestException;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.exceptions.UnauthorizedException;
import com.esm.alumniforum.security.CurrentUser;
import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.dto.ImageForm;
import com.esm.alumniforum.user.dto.ProfileForm;
import com.esm.alumniforum.user.dto.UsersForm;
import com.esm.alumniforum.user.dto.UsersResponse;
import com.esm.alumniforum.user.service.interfa.UsersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users/")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("create")
   public ResponseEntity<?> create(@Valid @RequestBody UsersForm form, BindingResult bindingResult, @CurrentUser UserPrincipal principal){
        try {
            if (!Objects.equals(form.getPassword(), form.getPassword2())) {
//                BadRequestException ex = new BadRequestException("Confirm Password must match password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestException("Confirm Password must match password").getApiResponse());
            }
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            UsersResponse response = this.usersService.save(form, principal);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BadRequestException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getApiResponse());
        }catch (UnauthorizedException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getApiResponse());
        }


}

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ProfileForm form){

        return new ResponseEntity<>(this.usersService.update(form), HttpStatus.OK);
    }
    @PostMapping("saveProfilePicture")
    public ResponseEntity<?> updateProfilePicture(@RequestBody ImageForm form){
        try {
            return new ResponseEntity<>(this.usersService.updateProfilePicture(form), HttpStatus.OK);
        }catch (ResourceNotFoundException ex){

            return new ResponseEntity<>(ex.getApiResponse(), HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(usersService.findById(id), headers, HttpStatus.OK);
    }
    @GetMapping("email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(usersService.findByEmail(email), headers, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<?> findAllPageable(@RequestParam(name = "page", required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) int size){
        HttpHeaders headers = new HttpHeaders();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new ResponseEntity<>(usersService.findAll(pageRequest), headers, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotBlank String id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            usersService.delete(id);
            return new ResponseEntity<>("Deleted Successfully", headers, HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException error){
            return new ResponseEntity<>(error.getApiResponse(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}

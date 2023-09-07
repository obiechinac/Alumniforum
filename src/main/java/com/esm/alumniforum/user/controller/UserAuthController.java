package com.esm.alumniforum.user.controller;

import com.esm.alumniforum.exceptions.LoginException;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.user.dto.LoginForm;
import com.esm.alumniforum.user.service.interfa.UserAuthService;
import jakarta.validation.Valid;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users/")
public class UserAuthController {

    @Autowired
    private UserAuthService usersService;

    @PostMapping(value="login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login( @Valid @RequestBody LoginForm loginForm) {
        try {
            Object loginResponse = usersService.login(loginForm);
            return ResponseEntity.ok(loginResponse);
        } catch (LoginException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getApiResponse());
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
//        catch (ResourceNotFoundException e){
//            return
//        }
    }

}

package com.esm.alumniforum.security;

import com.esm.alumniforum.exceptions.BadRequestException;
import com.esm.alumniforum.exceptions.ForbiddenException;
import com.esm.alumniforum.exceptions.UnauthorizedException;
import com.esm.alumniforum.user.dto.UsersForm;
import com.esm.alumniforum.user.dto.UsersResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/errors/")
public class AccessDeniedController {

    @GetMapping("access-denied")
    public ResponseEntity<?> create(HttpServletRequest request){


                Map<String, String> errors = new HashMap<>();
                errors.put("error", "You don't have access to this resource");
                ForbiddenException ex = new ForbiddenException("You don't have access to this resource");

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getApiResponse());


    }
}

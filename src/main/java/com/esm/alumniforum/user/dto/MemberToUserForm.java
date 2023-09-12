package com.esm.alumniforum.user.dto;

import com.esm.alumniforum.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberToUserForm {

    private String memberId;

    @Pattern(regexp = Constant.PASSWORD_REGEXP,
            message = "Password must contain one digit, one lowercase letter, one uppercase letter, " +
                    "one special character and must be 6-16 characters")
    private String password;
    private String password2;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    private String email;
}

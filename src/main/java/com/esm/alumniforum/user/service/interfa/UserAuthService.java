package com.esm.alumniforum.user.service.interfa;

import com.esm.alumniforum.user.dto.LoginForm;

public interface UserAuthService {
    Object login(LoginForm form);
}

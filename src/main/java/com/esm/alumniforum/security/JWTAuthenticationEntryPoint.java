package com.esm.alumniforum.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not authorized to access this resource.");

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // 401
        PrintWriter out = httpServletResponse.getWriter();

        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("code", httpStatus.value());
        data.put( "status", httpStatus.name() );
        data.put("message", e.getMessage());

        httpServletResponse.setStatus(httpStatus.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(data));
        out.flush();

    }
}

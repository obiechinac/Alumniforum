package com.esm.alumniforum.constant;

import com.esm.alumniforum.exceptions.APIException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

public class Constant {
    public static final String DEFAULT_PAGE_NUMBER = "0";

    public static final String DEFAULT_PAGE_SIZE = "20";

    public static final int MAX_PAGE_SIZE = 100;

    public static final String CREATED_AT = "createdAt";
    public static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$";

    public static final String ID = "id";

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new APIException(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new APIException(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
        }

        if (size > Constant.MAX_PAGE_SIZE) {
            throw new APIException(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Page size must not be greater than " + Constant.MAX_PAGE_SIZE);
        }
    }

    public static URI getUrl(HttpServletRequest request) throws MalformedURLException, URISyntaxException {
        URL url = new URL(request.getRequestURL().toString());
        String host  = url.getHost();
        String userInfo = url.getUserInfo();
        String scheme = url.getProtocol();
        String path = (String) request.getAttribute("jakarta.servlet.forward.request_uri");
        String query = (String) request.getAttribute("jakarta.servlet.forward.query_string");
        int port = url.getPort();
        URI uri = new URI(scheme,userInfo,host,port,path,query,null);
        System.out.println(uri.toString());
        return uri;
    }
}

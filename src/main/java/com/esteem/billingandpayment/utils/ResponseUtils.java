package com.esteem.billingandpayment.utils;

import java.util.HashMap;
import java.util.Map;

import com.esteem.billingandpayment.domain.SystemUser;

import org.springframework.security.core.Authentication;

public class ResponseUtils {

    private ResponseUtils() {

    }

    public static final String SUCCESS_MESSAGE = "success";
    public static final String ERROR_MESSAGE = "Error occured";
    public static final String INTERNAL_ERROR_MESSAGE = "Ooops, something went wrong on our end, please try again , if the error persist contact the administrator.";

    public static Map<String, Object> makeResponse(int code, String description, Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("description", description);
        res.put("data", data);
        return res;
    }

    public static String getDoneBy(Authentication auth) {
        if (auth == null) {
            return "dev";
        } else {
            SystemUser user = (SystemUser) auth.getPrincipal();
            return user.getUsername();
        }
    }

    public static SystemUser getUser(Authentication auth) {
        if (auth == null) {
            return new SystemUser();
        } else {
            return (SystemUser) auth.getPrincipal();
        }
    }
}
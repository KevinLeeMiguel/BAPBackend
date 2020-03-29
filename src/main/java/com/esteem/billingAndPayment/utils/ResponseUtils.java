package com.esteem.billingAndPayment.utils;

import java.util.HashMap;
import java.util.Map;

import com.esteem.billingAndPayment.domain.SystemUser;

import org.springframework.security.core.Authentication;

public class ResponseUtils {
    
    public static final String success_message = "success";
    public static final String error_message = "Error occured";
    public static final String internal_error_message = "Ooops, something went wrong on our end, please try again , if the error persist contact the administrator.";
    public static final String user_header_name = "doneBy";

    public static Map<String, Object> makeResponse(int code, String description, Object data) {
        Map<String, Object> res = new HashMap<String,Object>();
        res.put("code", code);
        res.put("description", description);
        res.put("data", data);
        return res;
    }

    public static String getDoneBy(Authentication auth){
        if(auth == null){
            return "dev"; 
        }else{
            SystemUser user = (SystemUser) auth.getPrincipal();
            return user.getUsername();
        }
    }

    public static SystemUser getUser(Authentication auth){
       if(auth==null){
           return new SystemUser();
       }else{
           return (SystemUser) auth.getPrincipal();
       }
    }
}
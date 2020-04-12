package com.esteem.billingandpayment.validations;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.esteem.billingandpayment.exceptions.CustomValidationException;

public class GeneralValidations {

    public String isValidString(String name, String val, int length) {
        if (val == null) {
            throw new CustomValidationException(name + " is required!");
        } else if (val.length() < 3) {
            throw new CustomValidationException(name + " must be longer than " + length + " characters!");
        } else {
            return name;
        }
    }

    public Double isValidDouble(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + " is required!");
        } else {
            try {
                double d = Double.parseDouble(val);
                return d;
            } catch (NumberFormatException e) {
                throw new CustomValidationException(name + " should be of type double!");
            }
        }
    }

    public String isPresent(String name, String st) {
        if (st == null) {
            throw new CustomValidationException(name + " is required!");
        } else {
            return st;
        }
    }

    public Date isValidDate(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + " is required!");
        } else {
            try {
                Date d = new SimpleDateFormat("dd-MM-yyyy").parse(val);
                return d;
            } catch (Exception e) {
                throw new CustomValidationException(name + " must be of date format (dd-MM-yyyy) !");
            }
        }
    }

    public Long isValidLong(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + " is required!");
        } else {
            try {
                Long l = Long.parseLong(val);
                return l;
            } catch (Exception e) {
                throw new CustomValidationException(name + " must be of type Long/number !");
            }
        }
    }

}
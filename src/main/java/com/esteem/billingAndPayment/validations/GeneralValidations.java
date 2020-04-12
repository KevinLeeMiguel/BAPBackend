package com.esteem.billingandpayment.validations;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.esteem.billingandpayment.exceptions.CustomValidationException;

public class GeneralValidations {

    public static final String REQUIRED = " is Required!";

    public String isValidString(String name, String val, int length) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else if (val.length() < 3) {
            throw new CustomValidationException(name + " must be longer than " + length + " characters!");
        } else {
            return name;
        }
    }

    public Double isValidDouble(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else {
            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                throw new CustomValidationException(name + " should be of type double!");
            }
        }
    }

    public String isPresent(String name, String st) {
        if (st == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else {
            return st;
        }
    }

    public Date isValidDate(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else {
            try {
                return new SimpleDateFormat("dd-MM-yyyy").parse(val);
            } catch (Exception e) {
                throw new CustomValidationException(name + " must be of date format (dd-MM-yyyy) !");
            }
        }
    }

    public Long isValidLong(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else {
            try {
                return Long.parseLong(val);
            } catch (Exception e) {
                throw new CustomValidationException(name + " must be of type Long/number !");
            }
        }
    }

}
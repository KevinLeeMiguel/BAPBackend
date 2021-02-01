package com.esteem.billingandpayment.validations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.esteem.billingandpayment.exceptions.CustomValidationException;

import org.springframework.format.datetime.DateFormatter;

public class GeneralValidations {

    public static final String REQUIRED = " is Required!";

    public String isValidString(String name, String val, int length) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else if (val.length() < length) {
            throw new CustomValidationException(name + " must be longer than " + (length - 1) + " characters!");
        } else {
            return val;
        }
    }

    public String isValidOptionalString(String name, String val, int length) {
        if (val == null || val.isEmpty()) {
            return null;
        } else if (val.length() < length) {
            throw new CustomValidationException(name + " must be longer than " + (length - 1) + " characters!");
        } else {
            return val;
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

    public LocalDate isValidDate(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else {
            try {
                String [] ds = val.split("-");
                ZoneOffset zoneOffSet= ZoneOffset.of("+02:00");
                return OffsetDateTime.of(LocalDate.of(Integer.parseInt(ds[2]), Month.of(Integer.parseInt(ds[1])), Integer.parseInt(ds[0])), LocalTime.MIDNIGHT, zoneOffSet).toLocalDate();
            } catch (Exception e) {
                e.printStackTrace();
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

    public String isValidEmail(String name, String val) {
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else if (val.contains(".") && val.contains("@")) {
            return val;
        } else {
            throw new CustomValidationException(name + " doesn't look like an email!");
        }
    }

    public String isValidPhone(String name, String val) {
        String errorMessage = " seems to be an invalid phone number";
        String start = "";
        if (val == null) {
            throw new CustomValidationException(name + REQUIRED);
        } else if (val.startsWith("+250") && val.length() == 13) {
            start = val.substring(3, 6);
            if (!Boolean.TRUE.equals(checkIfStringContainsOnlyNumbers(val.substring(1)))) {
                throw new CustomValidationException("after the '+' " + name + " should not contain characters");
            }
        } else if (val.length() == 10) {
            start = val.substring(0, 3);
            if (!Boolean.TRUE.equals(checkIfStringContainsOnlyNumbers(val))) {
                throw new CustomValidationException(name + " should not contain characters");
            }
        } else {
            throw new CustomValidationException(name + errorMessage);
        }
        if (start.equals("078") || start.equals("073") || start.equals("072"))
            return val;
        throw new CustomValidationException(
                name + " seems like a non rwandan phone number, Rwandan numbers starts with '078', '073', or '072'");
    }

    public Boolean checkIfStringContainsOnlyNumbers(String val) {
        return val.matches("[0-9]+");
    }

}

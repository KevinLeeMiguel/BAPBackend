package com.esteem.billingandpayment.controllers;

import java.util.Map;

import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.service.CustomerTypeService;
import com.esteem.billingandpayment.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/customertypes")
public class CustomerTypeController extends ResponseUtils {

    @Autowired
    private CustomerTypeService customerTypeService;

    @PostMapping(value = "")
    public ResponseEntity<Object> create(@RequestBody Map<String, String> req, Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, customerTypeService.create(req, doneBy)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Object> update(@PathVariable String uuid, @RequestBody Map<String, String> req,
            Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(
                    makeResponse(200, SUCCESS_MESSAGE, customerTypeService.update(uuid, req, doneBy)), HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Object> findByUuid(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, customerTypeService.findByUuid(uuid)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> findAll(Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, customerTypeService.findAll()),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

}
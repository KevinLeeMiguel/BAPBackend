package com.esteem.billingandpayment.controllers;

import java.util.Map;

import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.service.CustomerCategoryService;
import com.esteem.billingandpayment.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customercategories")
public class CustomerCategoryController extends ResponseUtils {

    @Autowired
    private CustomerCategoryService categoryService;

    @PostMapping(value = "/customertypes/{uuid}")
    public ResponseEntity<Object> create(@PathVariable String uuid, @RequestBody Map<String, String> req,
            Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, categoryService.create(uuid, req, doneBy)),
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
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, categoryService.update(uuid, req, doneBy)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Object> findByUuid(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, categoryService.findByUuid(uuid)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/customertypes/{uuid}")
    public ResponseEntity<Object> findByType(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, categoryService.findByType(uuid)),
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
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, categoryService.findAll()), HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }
}
package com.esteem.billingandpayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.CustomerCategory;
import com.esteem.billingandpayment.domain.CustomerType;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.CustomerCategoryRepo;
import com.esteem.billingandpayment.repo.CustomerTypeRepo;
import com.esteem.billingandpayment.validations.CustomerCategoryValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCategoryService {

    @Autowired
    private CustomerCategoryValidation Categoryvalidation;
    @Autowired
    private CustomerTypeRepo typeRepo;
    @Autowired
    private CustomerCategoryRepo categoryRepo;

    public CustomerCategory create(String uuid, Map<String, String> req, String doneBy) {
        Optional<CustomerType> type = typeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (type.isPresent()) {
            CustomerCategory c = Categoryvalidation.validate(req);
            Optional<CustomerCategory> cat = categoryRepo.findByNameAndTypeAndDeletedStatus(c.getName(), type.get(),
                    false);
            if (!cat.isPresent()) {
                c.setType(type.get());
                c.setDoneBy(doneBy);
                return categoryRepo.save(c);
            } else {
                throw new CustomValidationException("Category with name \"" + c.getName() + "\" already exists");
            }
        } else {
            throw new ObjectNotFoundException("Customer type not found!");
        }
    }

    public CustomerCategory update(String uuid, Map<String, String> req, String doneBy) {
        Optional<CustomerCategory> cc = categoryRepo.findByUuidAndDeletedStatus(uuid, false);
        if (cc.isPresent()) {
            CustomerCategory c = Categoryvalidation.validate(req);
            Optional<CustomerCategory> cat = categoryRepo.findByNameAndTypeAndDeletedStatus(c.getName(),
                    cc.get().getType(), false);
            if (!cat.isPresent()) {
                CustomerCategory fin = cc.get();
                fin.setDescription(c.getDescription());
                fin.setName(c.getName());
                fin.setLastUpdatedAt(new Date());
                fin.setLastUpdatedBy(doneBy);
                return categoryRepo.save(fin);
            } else {
                throw new CustomValidationException("Category with name \"" + c.getName() + "\" already exists!");
            }
        } else {
            throw new ObjectNotFoundException("the Customer Category you are trying to update was not found!");
        }
    }

    public CustomerCategory findByUuid(String uuid) {
        Optional<CustomerCategory> cat = categoryRepo.findByUuidAndDeletedStatus(uuid, false);
        if (cat.isPresent()) {
            return cat.get();
        } else {
            throw new ObjectNotFoundException("The Customer Category was not found");
        }
    }

    public List<CustomerCategory> findByType(String uuid) {
        return categoryRepo.findByTypeUuidAndDeletedStatus(uuid, false);
    }

    public List<CustomerCategory> findAll(String uuid) {
        return categoryRepo.findByDeletedStatus(false);
    }
}
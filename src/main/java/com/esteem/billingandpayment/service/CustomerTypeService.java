package com.esteem.billingandpayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.CustomerType;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.CustomerTypeRepo;
import com.esteem.billingandpayment.validations.CustomerTypeValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeService {

    @Autowired
    private CustomerTypeValidation typeValidation;
    @Autowired
    private CustomerTypeRepo typeRepo;

    public CustomerType create(Map<String, String> req, String doneBy) {
        CustomerType type = typeValidation.validate(req);

        Optional<CustomerType> t = typeRepo.findByNameAndDeletedStatus(type.getName(), false);
        if (!t.isPresent()) {
            type.setDoneBy(doneBy);
            return typeRepo.save(type);
        } else {
            throw new CustomValidationException("Customer type with name: '" + type.getName() + "' already exists");
        }
    }

    public CustomerType update(String uuid, Map<String, String> req, String doneBy) {
        Optional<CustomerType> t = typeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (t.isPresent()) {
            CustomerType type = t.get();
            CustomerType ty = typeValidation.validate(req);
            if (type.getName().equals(ty.getName())) {
                type.setDescription(ty.getDescription());
                return typeRepo.save(type);
            } else {
                Optional<CustomerType> typ = typeRepo.findByNameAndDeletedStatus(ty.getName(), false);
                if (!typ.isPresent()) {
                    type.setName(ty.getName());
                    type.setDescription(ty.getDescription());
                    type.setLastUpdatedAt(new Date());
                    type.setLastUpdatedBy(doneBy);
                    return typeRepo.save(type);
                } else {
                    throw new CustomValidationException(
                            "Customer type with name \"" + type.getName() + "\" already exists");
                }
            }

        } else {
            throw new ObjectNotFoundException("The customer you are trying to update was not found!");
        }
    }

    public CustomerType findByUuid(String uuid) {
        Optional<CustomerType> type = typeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (type.isPresent()) {
            return type.get();
        } else {
            throw new ObjectNotFoundException("Customer type not found!");
        }
    }

    public List<CustomerType> findAll(){
        return typeRepo.findByDeletedStatus(false);
    }
}

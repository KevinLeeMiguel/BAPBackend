package com.esteem.billingandpayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.ServiceE;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.ServiceRepo;
import com.esteem.billingandpayment.validations.ServiceValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

    @Autowired
    private ServiceValidation serviceValidation;
    @Autowired
    private ServiceRepo serviceRepo;

    public ServiceE create(Map<String, String> req, String doneBy) {
        ServiceE s = serviceValidation.validate(req);
        Optional<ServiceE> serv = serviceRepo.findByNameAndDeletedStatus(s.getName(), false);
        if (!serv.isPresent()) {
            s.setDoneBy(doneBy);
            return serviceRepo.save(s);
        } else {
            throw new ObjectAlreadyExistException("Service with name \"" + s.getName() + "\" already exists!");
        }
    }

    public ServiceE update(String uuid, Map<String, String> req, String doneBy) {
        Optional<ServiceE> s = serviceRepo.findByUuidAndDeletedStatus(uuid, false);
        if (s.isPresent()) {
            ServiceE ser = s.get();
            ServiceE upS = serviceValidation.validate(req);
            ser.setDescription(upS.getDescription());
            ser.setLastUpdatedAt(new Date());
            ser.setLastUpdatedBy(doneBy);
            if (ser.getName().equals(upS.getName())) {
                return serviceRepo.save(ser);
            } else {
                Optional<ServiceE> ss = serviceRepo.findByNameAndDeletedStatus(ser.getName(), false);
                if (!ss.isPresent()) {
                    ser.setName(upS.getName());
                    return serviceRepo.save(ser);
                } else {
                    throw new CustomValidationException("Service with name \"" + upS.getName() + "\" already exists!");
                }
            }
        } else {
            throw new ObjectNotFoundException("The service you are trying to update was not found!");
        }
    }

    public ServiceE findByUuid(String uuid) {
        Optional<ServiceE> service = serviceRepo.findByUuidAndDeletedStatus(uuid, false);
        if (service.isPresent()) {
            return service.get();
        } else {
            throw new ObjectNotFoundException("The service you are trying to find was not found!");
        }
    }

    public List<ServiceE> findAll() {
        return serviceRepo.findByDeletedStatus(false);
    }
}
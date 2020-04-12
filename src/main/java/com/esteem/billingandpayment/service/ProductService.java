package com.esteem.billingandpayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Product;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.ProductRepo;
import com.esteem.billingandpayment.validations.ProductValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductValidation productValidation;
    @Autowired
    private ProductRepo productRepo;

    public Product create(Map<String, String> req, String doneBy) {
        Product p = productValidation.validate(req);
        Optional<Product> product = productRepo.findByNameAndDeletedStatus(p.getName(), false);
        if (!product.isPresent()) {
            p.setDoneBy(doneBy);
            return productRepo.save(p);
        } else {
            throw new CustomValidationException("Product with name \"" + p.getName() + "\" already exists!");
        }

    }

    public Product update(String uuid, Map<String, String> req, String doneBy) {
        Optional<Product> p = productRepo.findByUuidAndDeletedStatus(uuid, false);
        if (p.isPresent()) {
            Product fin = p.get();
            Product pu = productValidation.validate(req);
            fin.setDescription(pu.getDescription());
            fin.setLastUpdatedBy(doneBy);
            fin.setLastUpdatedAt(new Date());
            if (pu.getName().equals(fin.getName())) {
                return productRepo.save(fin);
            } else {
                Optional<Product> product = productRepo.findByNameAndDeletedStatus(pu.getName(), false);
                if (!product.isPresent()) {
                    fin.setName(pu.getName());
                    return productRepo.save(fin);
                } else {
                    throw new CustomValidationException("Product with name \"" + pu.getName() + "\" already exists!");
                }
            }
        } else {
            throw new ObjectNotFoundException("The product you are trying to update was not found!");
        }
    }

    public Product findByUuid(String uuid) {
        Optional<Product> p = productRepo.findByUuidAndDeletedStatus(uuid, false);
        if (p.isPresent()) {
            return p.get();
        } else {
            throw new ObjectNotFoundException("The Product was not found!");
        }
    }

    public List<Product> products() {
        return productRepo.findByDeletedStatus(false);
    }

}

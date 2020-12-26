package com.esteem.billingandpayment.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Route;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.RouteRepo;
import com.esteem.billingandpayment.validations.RouteValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    @Autowired
    public RouteRepo routeRepo;
    @Autowired
    public RouteValidation validation;

    public Route create(Map<String, String> req, String doneBy) {
        Route route = validation.validate(req);
        route.setDoneBy(doneBy);
        if (Boolean.FALSE.equals(checkExistingName(route.getName())))
            return routeRepo.save(route);
        throw new CustomValidationException("Route with name '" + route.getName() + "' already exists!");
    }

    public Boolean checkExistingName(String name) {
        Optional<Route> route = routeRepo.findByNameAndDeletedStatus(name, false);
        if (route.isPresent())
            return true;
        return false;
    }

    public Route update(String uuid, Map<String, String> req, String doneBy) {
        Optional<Route> oRoute = routeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (oRoute.isPresent()) {
            Route route = oRoute.get();
            route.setLastUpdatedBy(doneBy);
            Route updatedRoute = validation.validate(req);
            route.setDescription(updatedRoute.getDescription());
            if (route.getName().equals(updatedRoute.getName()))
                return routeRepo.save(route);
            else {
                if (Boolean.FALSE.equals(checkExistingName(updatedRoute.getName()))){
                    route.setName(updatedRoute.getName());
                    return routeRepo.save(route);
                }
                throw new CustomValidationException("Route with name '" + updatedRoute.getName() + "' already exists!");
            }
        }
        throw new ObjectNotFoundException("The route you are trying to edit does not exist!");
    }

    public void delete(String uuid, String doneBy) {
        Optional<Route> oRoute = routeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (oRoute.isPresent()) {
            Route route = oRoute.get();
            route.setDeletedStatus(false);
            route.setLastUpdatedBy(doneBy);
            routeRepo.save(route);
        }
        throw new ObjectNotFoundException("The Route you are trying to delete does not exist!");
    }

    public Route findByUuid(String uuid) {
        Optional<Route> oRoute = routeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (oRoute.isPresent())
            return oRoute.get();
        throw new ObjectNotFoundException("Route not found!");
    }

    public List<Route> findAll() {
        return routeRepo.findByDeletedStatus(false);
    }
}

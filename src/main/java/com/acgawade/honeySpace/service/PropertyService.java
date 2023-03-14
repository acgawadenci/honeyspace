package com.acgawade.honeySpace.service;

import com.acgawade.honeySpace.entity.Property;
import com.acgawade.honeySpace.model.ResponseModel;
import com.acgawade.honeySpace.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.acgawade.honeySpace.config.Constants.STATUS_ACTIVE;
import static java.util.Objects.nonNull;


@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;
    public List<Property> fetchProperties() {
        return propertyRepository.findByStatus(STATUS_ACTIVE);
    }
    
    public ResponseModel saveProperty(Property property) {
        ResponseModel response=new ResponseModel();
        try{
            property.setStatus("A");
            property.setPostedOn(LocalDateTime.now());
            property.setPostedBy("userPrinciple");
            propertyRepository.save(property);
            response.setStatus("Success");
            response.setMessage("Operation Successful");
        } catch (Exception e){
            response.setException(e.getLocalizedMessage());
        }
        return response;
    }

    public ResponseModel updateProperty(UUID propertyId, Property property) {
        ResponseModel response=new ResponseModel();
        try{
            propertyRepository.findById(propertyId).ifPresent(
                    savedProperty -> {
                        if(savedProperty.getPostedBy().equalsIgnoreCase(property.getPostedBy())) {
                            setUpdatedValues(savedProperty, property);
                            propertyRepository.save(savedProperty);
                            response.setStatus("Success");
                            response.setMessage("Operation Successful");
                        } else {
                            response.setStatus("Unsuccessful");
                            response.setMessage("Operation Unsuccessful : Not Authorized To Update This Property");
                        }
                    }
            );
        } catch (Exception e){
            response.setStatus("Unsuccessful");
            response.setMessage("Exception Occurred");
            response.setException(e.getLocalizedMessage());
        }
        return response;
    }

    private void setUpdatedValues(Property property, Property updatedProp) {
        if(nonNull(updatedProp.getAddress()))
            property.setAddress(updatedProp.getAddress());

        if(nonNull(updatedProp.getArea()))
            property.setArea(updatedProp.getArea());

        if(nonNull(updatedProp.getBedrooms()))
            property.setBedrooms(updatedProp.getBedrooms());

        if(nonNull(updatedProp.getBathrooms()))
            property.setBathrooms(updatedProp.getBathrooms());

        if(nonNull(updatedProp.getEircode()))
            property.setEircode(updatedProp.getEircode());

        if(nonNull(updatedProp.getAmenities()))
            property.setAmenities(updatedProp.getAmenities());

        if(nonNull(updatedProp.getAvailableFrom()))
            property.setAvailableFrom(updatedProp.getAvailableFrom());

        if(nonNull(updatedProp.getEnergyRatings()))
            property.setEnergyRatings(updatedProp.getEnergyRatings());

        if(nonNull(updatedProp.getName()))
            property.setName(updatedProp.getName());

        if(nonNull(updatedProp.getPrice()))
            property.setPrice(updatedProp.getPrice());

        if(nonNull(updatedProp.getPostalCode()))
            property.setPostalCode(updatedProp.getPostalCode());

        property.setModifiedOn(LocalDateTime.now());
    }

    public ResponseModel inactiveProperty(UUID propertyId) {
        ResponseModel response=new ResponseModel();
        try{
            propertyRepository.findById(propertyId).ifPresent(
                    savedProperty -> {
                        if(savedProperty.getPostedBy().equalsIgnoreCase("userPrinciple")) {
                            savedProperty.setStatus("I");
                            propertyRepository.save(savedProperty);
                            response.setStatus("Success");
                            response.setMessage("Operation Successful");
                        } else {
                            response.setStatus("Unsuccessful");
                            response.setMessage("Operation Unsuccessful : Not Authorized To Update This Property");
                        }
                    }
            );
        } catch (Exception e){
            response.setStatus("Unsuccessful");
            response.setMessage("Exception Occurred");
            response.setException(e.getLocalizedMessage());
        }
        return response;
    }
}

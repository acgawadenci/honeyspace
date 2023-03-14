package com.acgawade.honeySpace.controller;

import com.acgawade.honeySpace.entity.Property;
import com.acgawade.honeySpace.model.ResponseModel;
import com.acgawade.honeySpace.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<Property>> getProperties() {
        return new ResponseEntity<>(propertyService.fetchProperties(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> postProperty(@RequestBody Property property) {
        return new ResponseEntity<>(propertyService.saveProperty(property), HttpStatus.CREATED);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<ResponseModel> updateProperty(@PathVariable UUID propertyId,
                                                        @RequestBody Property property) {
        return new ResponseEntity<>(propertyService.updateProperty(propertyId, property), HttpStatus.OK);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ResponseModel> deleteProperty(@PathVariable UUID propertyId) {
        return new ResponseEntity<>(propertyService.inactiveProperty(propertyId), HttpStatus.OK);
    }
}

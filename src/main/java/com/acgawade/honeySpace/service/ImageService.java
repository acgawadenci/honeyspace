package com.acgawade.honeySpace.service;

import com.acgawade.honeySpace.model.ResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    public ResponseModel storeImage(MultipartFile file, String propertyId) {
        ResponseModel response=new ResponseModel();
        response.setStatus("SUCCESS");
        response.setMessage("Image is uploaded");
        return response;
    }
}

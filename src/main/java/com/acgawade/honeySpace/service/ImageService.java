package com.acgawade.honeySpace.service;

import com.acgawade.honeySpace.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.sso.SsoClient;
import software.amazon.awssdk.services.sso.auth.SsoCredentialsProvider;

import java.io.*;
import java.util.UUID;

@Service
public class ImageService {
    private static final String BUCKET = "x22103228-cpp";
    @Autowired
    AwsSessionCredentials awsSessionCredentials;
    @Autowired
    PropertyService propertyService;

    private String propertyId;

    public ResponseModel storeImage(MultipartFile file, String propertyId) {
        ResponseModel response = new ResponseModel();
        this.propertyId = propertyId;

        try {
            String resp = uploadFile(generateFileName(propertyId, file.getContentType().split("/")[1]), file.getInputStream());
            response.setStatus("SUCCESS");
            response.setMessage(resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private String uploadFile(String fileName, InputStream inputStream) {
        try (S3Client client = S3Client.builder().region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsSessionCredentials))
                .build()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(fileName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            PutObjectResponse response = client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
            propertyService.updateImageUrl(propertyId, fileName);
            return response.eTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(String propertyId, String contentType) {
        return propertyId + "/" + UUID.randomUUID() + "." + contentType;
    }


    public void imageTest() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String s;
        try {
            processBuilder.command("/bin/aws", "-c", "aws s3 cp /var/app/current/src/main/resources/static/axe.jpg s3://x22103228-cpp/axe.jpg --profile 22103228-dev");
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitVal = process.waitFor();

            if (exitVal == 0) {
                System.out.println("Success! Message: " + output);
            } else {
                System.out.println("Failed! Message: " + output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.foodway.api.controller;
import com.foodway.api.config.AmazonS3Config;
import com.foodway.api.service.StorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Properties;
import com.foodway.api.model.S3Credentials;
import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/files")
@Tag(name = "S3")
public class S3Controller {
    @Autowired
    private AmazonS3Config s3Config;
    @Autowired
    private Environment environment;
    @Autowired
    private StorageService s3Service;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path){
        return s3Service.saveFile(file, path);
    }

    @PostMapping("/update-credentials")
    public String updateCredentials(@RequestBody S3Credentials request) throws IOException {
        System.out.println(request);
        Properties properties = new Properties();
        properties.setProperty("accessKey", request.getAccessKey());
        properties.setProperty("secret", request.getSecret());
        properties.setProperty("region", request.getRegion());
        properties.setProperty("bucketName", request.getBucketName());

        try (FileOutputStream outputStream = new FileOutputStream("src/main/resources/s3.properties")) {
            properties.store(outputStream, null);
        }

        s3Config.setAccessKey(request.getAccessKey());
        s3Config.setSecret(request.getSecret());
        s3Config.setRegion(request.getRegion());
        s3Config.setBucketName(request.getBucketName());
        s3Config.setSessionToken(request.getSessionToken());

        return "Credenciais atualizadas com sucesso.";
    }

    @GetMapping("/credentials")
    public S3Credentials getCredentials() {
        return new S3Credentials(s3Config.getAccessKey(), s3Config.getSecret(), s3Config.getRegion(), s3Config.getBucketName(), s3Config.getSessionToken());
    }
}
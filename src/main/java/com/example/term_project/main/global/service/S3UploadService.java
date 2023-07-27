package com.example.term_project.main.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;
    private final Logger LOGGER = LoggerFactory.getLogger(S3UploadService.class);

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        LOGGER.info("debug1");
        String originalFilename = multipartFile.getOriginalFilename();
        LOGGER.info("debug2");

        ObjectMetadata metadata = new ObjectMetadata();
        LOGGER.info("debug3");

        metadata.setContentLength(multipartFile.getSize());
        LOGGER.info("debug4");

        metadata.setContentType(multipartFile.getContentType());
        LOGGER.info("debug5");

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        LOGGER.info("debug6");

        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public void deleteImage(String originalFilename)  {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}

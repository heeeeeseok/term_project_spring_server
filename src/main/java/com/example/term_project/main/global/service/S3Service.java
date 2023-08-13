package com.example.term_project.main.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) throws IOException, ResponseException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseException(ResponseCode.S3_UPLOAD_FAILED);
        }

        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public void deleteFile(String fileUrl) throws ResponseException {
        try {
            boolean isObjectExist = amazonS3.doesObjectExist(bucket, fileUrl);
            if (isObjectExist) {
                amazonS3.deleteObject(bucket, fileUrl);
            } else {
                log.debug("fail: Delete File not found");
                throw new ResponseException(ResponseCode.S3_DELETE_FAILED);
            }
        } catch (Exception e) {
            log.debug("fail: Delete File failed", e);
            throw new ResponseException(ResponseCode.S3_DELETE_FAILED);
        }
    }

    public boolean isExistFile(String fileUrl) {
        return amazonS3.doesObjectExist(bucket, fileUrl);
    }
}

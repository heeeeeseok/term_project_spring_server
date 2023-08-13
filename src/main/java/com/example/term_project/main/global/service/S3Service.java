package com.example.term_project.main.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.term_project.main.domain.post.PostService;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;
    private final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) throws IOException, ResponseException {
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = createFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, newFileName, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseException(ResponseCode.S3_UPLOAD_FAILED);
        }

        LOGGER.info("filename: " + newFileName);
        return amazonS3.getUrl(bucket, newFileName).toString();
    }

    public void deleteFile(String fileUrl) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException se) {
            LOGGER.info("잘못된 형식의 파일입니다.");
            throw new ResponseException(ResponseCode.S3_INVALID_FILE);
        }
    }
}

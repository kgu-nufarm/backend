package api.kgu.nufarm.common.file.service;

import api.kgu.nufarm.application.user.service.UserService;
import api.kgu.nufarm.common.file.exception.FileStorageException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String storeUserItemFile(MultipartFile file) {
        try {
            // 현재 로그인된 사용자의 ID 가져오기
            Long userId = userService.getCurrentUser().getId();

            // 사용자별 폴더 경로 생성
            String userFolder = "user_" + userId;
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 메타데이터 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3에 파일 업로드
            InputStream fileInputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, userFolder + "/" + fileName, fileInputStream, metadata);
            amazonS3Client.putObject(putObjectRequest);

            // 저장된 파일의 경로 반환
            return "https://" + bucketName + ".s3.amazonaws.com/" + userFolder + "/" + fileName;
        } catch (IOException e) {
            throw new FileStorageException("파일을 저장할 수 없습니다: " + file.getOriginalFilename(), e);
        }
    }

    public String storeItemFile(MultipartFile file) {
        System.out.println(bucketName);
        try {
            String folder = "items";
            String fileName = file.getOriginalFilename();

            // 메타데이터 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3에 파일 업로드
            InputStream fileInputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folder + "/" + fileName, fileInputStream, metadata);
            amazonS3Client.putObject(putObjectRequest);

            // 저장된 파일의 경로 반환
            return "https://" + bucketName + ".s3.amazonaws.com/" + folder + "/" + fileName;
        } catch (IOException e) {
            throw new FileStorageException("파일을 저장할 수 없습니다: " + file.getOriginalFilename(), e);
        }
    }
}

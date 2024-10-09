package api.kgu.nufarm.common.file.service;

import api.kgu.nufarm.common.file.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "uploads/";

    public String storeFile(MultipartFile file) {
        try {
            // 날짜별로 폴더 생성 (예: uploads/2024/10/06/)
            String dateFolder = LocalDate.now().toString().replace("-", "/"); // 년/월/일 폴더 생성
            Path directoryPath = Paths.get(uploadDir + dateFolder);

            // 폴더가 없으면 생성
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);  // 경로에 있는 모든 상위 폴더도 생성
            }

            // 파일명 처리 (중복 방지를 위해 UUID 사용)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = directoryPath.resolve(fileName);  // 날짜별 폴더에 파일 저장 경로 설정

            // 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 파일의 경로 반환 (예: /uploads/2024/10/06/filename.jpg)
            return "/uploads/" + dateFolder + "/" + fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + file.getOriginalFilename(), e);
        }
    }
}

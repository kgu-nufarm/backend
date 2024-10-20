package api.kgu.nufarm.common.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Tag(name = "File", description = "파일 입출력")
public class FileController {

    @Operation(summary = "이미지 조회")
    @GetMapping("/getImage")
    public ResponseEntity<String> serveUserItemFile(
            @RequestParam String imageUrl
    ) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1) + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(imageUrl);
    }
}

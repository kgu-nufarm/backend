package api.kgu.nufarm.application.like.api;

import api.kgu.nufarm.application.like.dto.LikeResponseDto;
import api.kgu.nufarm.application.like.service.LikeService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
@Tag(name = "Like", description = "아이템 즐겨찾기")
public class LikeController {

    private final LikeService likeService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "즐겨찾기 추가")
    @PostMapping("/add")
    public ApiResponse<Long> addLikeItem(
            @RequestBody Long itemId
    ) {
        Long id = likeService.addLikeItem(itemId);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "즐겨찾기 삭제")
    @DeleteMapping("/remove")
    public ApiResponse<Long> deleteLikeItem(
            @RequestBody Long itemId
    ) {
        Long id = likeService.deleteLikeItem(itemId);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "즐겨찾기 목록 조회")
    @GetMapping("/get")
    public ApiResponse<List<LikeResponseDto>> getLikeItems() {
        List<LikeResponseDto> likeItems = likeService.getLikeItems();
        return ApiResponse.success(likeItems);
    }
}

package api.kgu.nufarm.application.useritem.api;

import api.kgu.nufarm.application.useritem.dto.AddUserItemRequestDto;
import api.kgu.nufarm.application.useritem.dto.UserItemResponseDto;
import api.kgu.nufarm.application.useritem.service.UserItemService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userItem")
@Tag(name = "UserItem", description = "개인 작물")
public class UserItemController {

    private final UserItemService userItemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "작물 추가")
    @PostMapping(value ="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Long> addUserItem(
            HttpServletRequest request,
            @RequestPart AddUserItemRequestDto dto,
            @RequestPart MultipartFile photoFile
    ) {
        Long id = userItemService.addUserItem(request, dto, photoFile);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "작물 조회")
    @GetMapping("/get")
    public ApiResponse<List<UserItemResponseDto>> getUserItem() {
        List<UserItemResponseDto> dtos = userItemService.getMyUserItem();
        return ApiResponse.success(dtos);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "작물 즐겨찾기 추가")
    @PostMapping("/addStar/{id}")
    public ApiResponse<Long> starUserItem(
            @PathVariable Long id
    ) {
        userItemService.starUserItem(id);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "작물 즐겨찾기 제거")
    @PostMapping("/removeStar/{id}")
    public ApiResponse<Long> unStarUserItem(
            @PathVariable Long id
    ) {
        userItemService.unStarUserItem(id);
        return ApiResponse.success(id);
    }
}

package api.kgu.nufarm.application.Item.api;

import api.kgu.nufarm.application.Item.dto.ItemRequestDto;
import api.kgu.nufarm.application.Item.dto.ItemResponseDto;
import api.kgu.nufarm.application.Item.service.ItemService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name = "Item", description = "상점 물품")
public class ItemController {

    private final ItemService itemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "물품 등록")
    @PostMapping("/add")
    public ApiResponse<Long> addItem(
            @RequestBody ItemRequestDto dto
    ) {
        Long id = itemService.addItem(dto);
        return ApiResponse.success(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "카테고리별 물품 조회")
    @GetMapping("/category")
    public ApiResponse<List<ItemResponseDto>> getItemsByCategory(
            @RequestParam String category
    ) {
        List<ItemResponseDto> items = itemService.getItemsByCategory(category);
        return ApiResponse.success(items);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "물품 검색")
    @GetMapping("/search")
    public ApiResponse<List<ItemResponseDto>> getItemsBySearch(
            @RequestParam String search
    ) {
        List<ItemResponseDto> items = itemService.getItemsBySearch(search);
        return ApiResponse.success(items);
    }
}

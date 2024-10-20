package api.kgu.nufarm.application.Item.service;

import api.kgu.nufarm.application.Item.dao.ItemRepository;
import api.kgu.nufarm.application.Item.dto.ItemRequestDto;
import api.kgu.nufarm.application.Item.dto.ItemResponseDto;
import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.entity.ItemCategory;
import api.kgu.nufarm.application.like.dao.LikeItemRepository;
import api.kgu.nufarm.application.like.entity.LikeItem;
import api.kgu.nufarm.application.user.service.UserService;
import api.kgu.nufarm.common.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final LikeItemRepository likeItemRepository;
    private final FileService fileService;

    @Transactional
    public Long addItem(ItemRequestDto dto, MultipartFile file) {
        String imageUrl = fileService.storeItemFile(file);
        Item item = ItemRequestDto.toEntity(dto, imageUrl);
        return itemRepository.save(item).getId();
    }

    public List<ItemResponseDto> getItemsByCategory(String category) {
        List<Item> items = itemRepository.findByCategory(ItemCategory.valueOf(category));
        return getItemResponseDtoFromItems(items);
    }

    public List<ItemResponseDto> getItemsBySearch(String search) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCase(search);
        return getItemResponseDtoFromItems(items);
    }

    public List<ItemResponseDto> getItemResponseDtoFromItems(List<Item> items) {
        List<LikeItem> likeItems = likeItemRepository.findByUserId(userService.getCurrentUser().getId());

        Set<Long> likedItemIds = likeItems.stream()
                .map(likeItem -> likeItem.getItem().getId())
                .collect(Collectors.toSet());

        return items.stream()
                .map(item -> {
                    boolean isLike = likedItemIds.contains(item.getId());
                    return ItemResponseDto.toDto(item, isLike);
                })
                .toList();
    }

    public Item findByItemId(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }
}

package api.kgu.nufarm.application.Item.service;

import api.kgu.nufarm.application.Item.dao.ItemRepository;
import api.kgu.nufarm.application.Item.dto.ItemRequestDto;
import api.kgu.nufarm.application.Item.dto.ItemResponseDto;
import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.entity.ItemCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long addItem(ItemRequestDto dto) {
        Item item = ItemRequestDto.toEntity(dto);
        return itemRepository.save(item).getId();
    }

    public List<ItemResponseDto> getItemsByCategory(String category) {
        List<Item> items = itemRepository.findByCategory(ItemCategory.valueOf(category));
        return items.stream()
                .map(ItemResponseDto::toDto)
                .toList();
    }

    public List<ItemResponseDto> getItemsBySearch(String search) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCase(search);
        return items.stream()
                .map(ItemResponseDto::toDto)
                .toList();
    }

    public Item findByItemId(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }
}

package api.kgu.nufarm.application.like.service;

import api.kgu.nufarm.application.Item.dao.ItemRepository;
import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.service.ItemService;
import api.kgu.nufarm.application.like.dao.LikeItemRepository;
import api.kgu.nufarm.application.like.dto.LikeResponseDto;
import api.kgu.nufarm.application.like.entity.LikeItem;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserService userService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final LikeItemRepository likeItemRepository;

    @Transactional
    public Long addLikeItem(Long itemId) {
        Item item = itemService.getItem(itemId);
        LikeItem likeItem = LikeItem.builder()
                .item(item)
                .user(userService.getCurrentUser())
                .build();
        return likeItemRepository.save(likeItem).getId();
    }

    @Transactional
    public Long deleteLikeItem(Long itemId) {
        Item item = itemService.getItem(itemId);
        User user = userService.getCurrentUser();
        LikeItem likeItem = likeItemRepository.findByItemIdAndUserId(item.getId(), user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 아이템이 없습니다."));
        likeItemRepository.delete(likeItem);
        return likeItem.getId();
    }

    public List<LikeResponseDto> getLikeItems() {
        User user = userService.getCurrentUser();
        List<LikeItem> likeItems = likeItemRepository.findByUserId(user.getId());
        if(likeItems.isEmpty()) {
            return null;
        }
        return likeItems.stream()
                .map(LikeResponseDto::toDto)
                .toList();

    }
}

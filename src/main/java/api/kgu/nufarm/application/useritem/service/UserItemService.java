package api.kgu.nufarm.application.useritem.service;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.service.ItemService;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.user.service.UserService;
import api.kgu.nufarm.application.useritem.dao.UserItemRepository;
import api.kgu.nufarm.application.useritem.dto.AddUserItemRequestDto;
import api.kgu.nufarm.application.useritem.dto.UserItemResponseDto;
import api.kgu.nufarm.application.useritem.entity.UserItem;
import api.kgu.nufarm.common.file.service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserItemRepository userItemRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final FileService fileService;

    @Transactional
    public Long addUserItem(AddUserItemRequestDto dto, MultipartFile photoFile) {
        User currentUser = userService.getCurrentUser();
        Item item = itemService.findByItemId(dto.getId());

        String photoUrl = fileService.storeUserItemFile(photoFile);

        UserItem userItem = UserItem.create(currentUser, item, dto, photoUrl);
        userItemRepository.save(userItem);
        return userItem.getId();
    }

    public UserItem getUserItem(Long id) {
        return userItemRepository.findById(id).orElse(null);
    }

    public List<UserItemResponseDto> getMyUserItem() {
        User currentUser = userService.getCurrentUser();
        List<UserItem> userItems = userItemRepository.findByUserId(currentUser.getId());
        if(userItems == null || userItems.isEmpty()) {
            return null;
        }
        return userItems.stream()
                .map(UserItemResponseDto::toDto)
                .toList();
    }

    public void starUserItem(Long id) {
        updateStarUserItem(id, true);
    }

    public void unStarUserItem(Long id) {
        updateStarUserItem(id, false);
    }

    public void updateStarUserItem(Long id, Boolean star) {
        UserItem userItem = userItemRepository.findById(id).orElse(null);

        if(userItem == null) {
            throw new RuntimeException("해당 id의 작물이 없습니다.");
        }
        userItem.updateStar(star);
        userItemRepository.save(userItem);
    }
}

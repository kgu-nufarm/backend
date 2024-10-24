package api.kgu.nufarm.application.useritem.entity;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.useritem.dto.AddUserItemRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 작물

    @Column(nullable = false)
    private LocalDateTime createdAt; // 작물 시작 시간

    private LocalDateTime lastWateredTime; // 마지막 물 준 시간

    private boolean isAbnormal;  // 이상 여부

    private String abnormalInfo; // 이상 상세 정보

    private boolean isStared; // 즐겨찾기 여부

    private String photoUrl; // 사진

    public static UserItem create(User user, Item item, AddUserItemRequestDto dto, String photoUrl) {
        return UserItem.builder()
                .user(user)
                .item(item)
                .createdAt(dto.getAddedDate())
                .photoUrl(photoUrl)
                .build();
    }

    public void updateStar(boolean isStared) {
        this.isStared = isStared;
    }
}

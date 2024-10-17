package api.kgu.nufarm.application.Item.entity;

import api.kgu.nufarm.application.useritem.entity.UserItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 제품명

    @Column(nullable = false)
    private String description; // 제품 설명

    @Column(nullable = false)
    private Integer price; // 가격

    @Column(nullable = false)
    private String imageUrl; // 이미지 URL

    @Enumerated(EnumType.STRING)
    private ItemCategory category; // 상품 카테고리

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<UserItem> userItems; // 제품을 선택한 사용자 목록
}

package api.kgu.nufarm.application.user.entity;

import api.kgu.nufarm.application.cart.entity.CartItem;
import api.kgu.nufarm.application.like.entity.LikeItem;
import api.kgu.nufarm.application.user.dto.request.UserRequestDto;
import api.kgu.nufarm.application.useritem.entity.UserItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(length = 10, nullable = false)
    private String username; // 닉네임

    private Role role; // 권한

    @OneToMany(mappedBy = "user")
    private List<LikeItem> likes; // 즐겨찾기 목록

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems; // 장바구니 목록

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserItem> userItems; // 회원의 작물 목록

    public static User toEntity(UserRequestDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}

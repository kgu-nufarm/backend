package api.kgu.nufarm.application.like.dao;

import api.kgu.nufarm.application.like.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {

    Optional<LikeItem> findByItemIdAndUserId(Long itemId, Long userId);

    List<LikeItem> findByUserId(Long userId);
}

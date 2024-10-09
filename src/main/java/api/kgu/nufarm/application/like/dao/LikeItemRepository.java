package api.kgu.nufarm.application.like.dao;

import api.kgu.nufarm.application.like.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {
}

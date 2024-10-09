package api.kgu.nufarm.application.Item.dao;

import api.kgu.nufarm.application.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}

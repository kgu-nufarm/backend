package api.kgu.nufarm.application.Item.dao;

import api.kgu.nufarm.application.Item.entity.Item;
import api.kgu.nufarm.application.Item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategory(ItemCategory category);
    List<Item> findByNameContainingIgnoreCase(String name);
}

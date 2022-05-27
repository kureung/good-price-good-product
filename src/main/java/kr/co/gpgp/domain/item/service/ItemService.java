package kr.co.gpgp.domain.item.service;

import java.util.List;
import kr.co.gpgp.domain.item.entity.Item;

public interface ItemService {
    Long save(Item item);

    void update(Long itemId, Item item);

    List<Item> findAll();

    Item findOne(Long itemId);
}

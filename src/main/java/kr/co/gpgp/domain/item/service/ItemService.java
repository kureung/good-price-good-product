package kr.co.gpgp.domain.item.service;

import kr.co.gpgp.domain.item.entity.Item;

public interface ItemService {

    Long save(Item item);

    void update(Long itemId, Item item);

    Item findOne(Long itemId);
}

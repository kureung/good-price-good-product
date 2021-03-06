package kr.co.gpgp.domain.item;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository {

    Optional<Item> findByInfoCode(String code);

    boolean existsByInfoName(String name);

    Page<ItemSearchDto> searchItem(ItemSearchCondition condition, Pageable pageable);

    Optional<Item> findById(Long id);

    Item save(Item item);

}

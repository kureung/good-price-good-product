package kr.co.gpgp.domain.item;

import java.util.Optional;
import kr.co.gpgp.web.api.item.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository {

    Optional<Item> findByInfoCode(String code);

    boolean existsByInfoCode(String code);

    Page<ItemResponse> searchItem(ItemSearchCondition condition, Pageable pageable);

}

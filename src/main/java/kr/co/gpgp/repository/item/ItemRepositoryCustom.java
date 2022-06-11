package kr.co.gpgp.repository.item;

import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemResponse> searchItem(ItemSearchCondition condition, Pageable pageable);

}

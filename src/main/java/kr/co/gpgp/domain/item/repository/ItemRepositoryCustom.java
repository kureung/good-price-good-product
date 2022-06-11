package kr.co.gpgp.domain.item.repository;

import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.search.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemResponse> searchItem(ItemSearchCondition condition, Pageable pageable);

}

package kr.co.gpgp.repository.item;

import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import kr.co.gpgp.domain.item.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemSearchDto> searchItem(ItemSearchCondition condition, Pageable pageable);

}

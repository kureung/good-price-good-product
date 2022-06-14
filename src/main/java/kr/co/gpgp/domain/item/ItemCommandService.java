package kr.co.gpgp.domain.item;

import kr.co.gpgp.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandService {

    private final ItemRepository itemRepository;
    private final ItemFindService itemFindService;

    public Item save(Item item) {
        if (itemRepository.existsByInfoCode(item.getCode())) {
            throw new IllegalStateException(ErrorCode.ITEM_DUPLICATE_CHECK_ERROR.getMessage());
        }

        return itemRepository.save(item);
    }

    public Long update(Long itemId, Item item) {
        Item findItem = itemFindService.findOne(itemId);
        findItem.update(item.getPrice(), item.getStockQuantity(), item.getInfo());
        return findItem.getId();
    }

}

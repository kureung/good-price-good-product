package kr.co.gpgp.domain.item;

import kr.co.gpgp.web.api.item.ItemRequest;
import kr.co.gpgp.web.api.item.ItemResponse;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandService {

    private final ItemJpaRepository itemJpaRepository;
    private final ItemFindService itemFindService;
    private final ItemDtoService itemDtoService;

    public Long save(Item item) {
        if (itemJpaRepository.existsByInfoCode(item.getCode())) {
            throw new IllegalStateException(ErrorCode.ITEM_DUPLICATE_CHECK_ERROR.getMessage());
        }

        Item savedItem = itemJpaRepository.save(item);
        return savedItem.getId();
    }

    public Long update(Long itemId, Item item) {
        Item findItem = itemFindService.findOne(itemId);
        findItem.update(item.getPrice(), item.getStockQuantity(), item.getInfo());
        return findItem.getId();
    }

    public ItemResponse register(ItemRequest request) {
        Item item = itemDtoService.toEntity(request);
        Long itemId = save(item);
        Item findItem = itemFindService.findOne(itemId);
        return itemDtoService.toDto(findItem);
    }

}

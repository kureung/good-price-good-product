package kr.co.gpgp.domain.item.service;

import kr.co.gpgp.domain.item.dto.ItemRequest;
import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import kr.co.gpgp.domain.item.service.dto.ItemDtoService;
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
    private final ItemDtoService itemDtoService;

    public Long save(Item item) {
        if (itemRepository.existsByInfoCode(item.getCode())) {
            throw new IllegalStateException(ErrorCode.ITEM_DUPLICATE_CHECK_ERROR.getMessage());
        }

        Item savedItem = itemRepository.save(item);
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

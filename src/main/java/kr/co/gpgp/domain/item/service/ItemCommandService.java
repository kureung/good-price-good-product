package kr.co.gpgp.domain.item.service;

import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import kr.co.gpgp.domain.item.service.dto.ItemDtoService;
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
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    public Long update(Long itemId, Item item) {
        Item findItem = itemFindService.findOne(itemId);
        findItem.update(item.getPrice(), item.getStockQuantity(), item.getInfo());
        return findItem.getId();
    }

    public ItemDtoResponse register(ItemDtoRequest request) {
        Item item = itemDtoService.dtoConversionItem(request);
        Long itemId = save(item);
        Item findItem = itemFindService.findOne(itemId);
        return itemDtoService.itemConversionDto(findItem);
    }
}

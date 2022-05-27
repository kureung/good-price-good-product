package kr.co.gpgp.domain.item.service;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public Long save(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    @Override
    public void update(Long itemId, Item item) {
        Item findItem = findOne(itemId);
        findItem.update(item.getPrice(), item.getStockQuantity(), item.getInfo());
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findOne(Long itemId) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        validationFindItem(findItemOptional);
        return findItemOptional.orElse(null);
    }

    private void validationFindItem(Optional<Item> findItemOptional) {
        if (findItemOptional.isEmpty()) {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }
}

package kr.co.gpgp.domain.item.service;

import java.util.NoSuchElementException;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemFindService {

    private final ItemRepository itemRepository;

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
    }
}
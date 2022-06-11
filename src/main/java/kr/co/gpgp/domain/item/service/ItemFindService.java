package kr.co.gpgp.domain.item.service;

import java.util.NoSuchElementException;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemFindService {

    private final ItemJpaRepository itemJpaRepository;

    public Item findOne(Long itemId) {
        return itemJpaRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));
    }

    public Item findOne(String itemCode) {
        return itemJpaRepository.findByInfoCode(itemCode)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));
    }
}

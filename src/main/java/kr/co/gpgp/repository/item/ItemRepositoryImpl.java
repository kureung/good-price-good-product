package kr.co.gpgp.repository.item;

import java.util.Optional;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemRepository;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository jpaRepository;
    private final ItemRepositoryCustom repositoryCustom;

    @Override
    public Optional<Item> findByInfoCode(String code) {
        return jpaRepository.findByInfoName(code);
    }

    @Override
    public boolean existsByInfoName(String name) {
        return jpaRepository.existsByInfoName(name);
    }

    @Override
    public Page<Item> searchItem(ItemSearchCondition condition, Pageable pageable) {
        return repositoryCustom.searchItem(condition, pageable);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return jpaRepository.save(item);
    }

}

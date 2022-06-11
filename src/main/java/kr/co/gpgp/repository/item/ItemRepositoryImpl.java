package kr.co.gpgp.repository.item;

import java.util.Optional;
import kr.co.gpgp.domain.item.ItemRepository;
import kr.co.gpgp.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository jpaRepository;
    private final ItemRepositoryCustom repositoryCustom;

    @Override
    public Optional<Item> findByInfoCode(String code) {
        return jpaRepository.findByInfoCode(code);
    }

    @Override
    public boolean existsByInfoCode(String code) {
        return jpaRepository.existsByInfoCode(code);
    }

}

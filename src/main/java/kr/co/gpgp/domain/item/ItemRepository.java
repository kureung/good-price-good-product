package kr.co.gpgp.domain.item;

import java.util.Optional;
import kr.co.gpgp.domain.item.entity.Item;

public interface ItemRepository {

    Optional<Item> findByInfoCode(String code);

    boolean existsByInfoCode(String code);

}

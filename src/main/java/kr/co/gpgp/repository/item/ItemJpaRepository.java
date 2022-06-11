package kr.co.gpgp.repository.item;

import java.util.Optional;
import kr.co.gpgp.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByInfoCode(String code);

    boolean existsByInfoCode(String code);

}

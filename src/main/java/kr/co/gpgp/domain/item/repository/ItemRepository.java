package kr.co.gpgp.domain.item.repository;

import java.util.Optional;
import kr.co.gpgp.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByInfoCode(String code);
}

package kr.co.gpgp.repository.user;

import java.util.Optional;
import kr.co.gpgp.domain.user.Seller;
import kr.co.gpgp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerJpaRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByUser(User user);

}

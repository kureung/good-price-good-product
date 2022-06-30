package kr.co.gpgp.domain.user;

import java.util.Optional;

public interface SellerRepository {

    Optional<Seller> findByUser(User user);

    void save(Seller seller);
}

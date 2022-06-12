package kr.co.gpgp.repository.user;

import java.util.Optional;
import kr.co.gpgp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserJpaRepository  extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
